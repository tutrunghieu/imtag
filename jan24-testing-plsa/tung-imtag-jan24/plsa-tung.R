rm(list=ls());
setwd('C:/Users/henrytu/Desktop/tung-imtag-jan24')


pLSA_2 <- function(BOW, nTopics, nrep = 20, threshold = 0) {
    nImages = nrow(BOW)
    nWords = ncol(BOW);

    # p(z|d,w)
    getP_z_dw <- function(z, d, w){
        return(p_z[z] * p_d_z[d, z] * p_w_z[w, z] / p_dw[d, w])
    }
    
    # initialize table of p(d,w)
    p_dw = matrix(nrow = nImages, ncol = nWords)
    
    #initialize p(w|z), p(d|z), p(z)
    p_w_z = matrix(data = runif(nWords * nTopics), nrow = nWords)
    for(i in 1 : nTopics)
        p_w_z[, i] = p_w_z[, i] / sum(p_w_z[, i])
    p_d_z = matrix(data = runif(nImages * nTopics), nrow = nImages)
    for(i in 1 : nTopics)
        p_d_z[, i] = p_d_z[, i] / sum(p_d_z[, i])
    p_z = runif(nTopics)
    p_z = p_z / sum(p_z)
    
    lastLikelihood = 0
    for(d in 1 : nImages){
        for(w in 1 : nWords){
            if(BOW[d, w] == 0)
                next
            sum = 0
            for(z in 1 : nTopics)
                sum = sum + p_z[z] * p_d_z[d, z] * p_w_z[w, z]
            p_dw[d, w] = sum
            lastLikelihood = lastLikelihood + log(sum) * BOW[d, w]
        }
    }
    
    for(rep in 1 : nrep){
        cat('Iteration ', rep, '\n');

        #initialize new p(w|z), p(d|z), p(z)
        p2_w_z = matrix(data = 0, nrow = nWords, ncol = nTopics)
        p2_d_z = matrix(data = 0, nrow = nImages, ncol = nTopics)
        p2_z = matrix(data = rep(0, nTopics))
        
        for(d in 1 : nImages){
            for(w in 1 : nWords){
                if(BOW[d, w] == 0)
                    next
                for(z in 1 : nTopics){
                    addition = BOW[d, w] * getP_z_dw(z, d, w)
                    p2_w_z[w, z] = p2_w_z[w, z] + addition
                    p2_d_z[d, z] = p2_d_z[d, z] + addition
                    p2_z[z] = p2_z[z] + addition
                }
            }
        }
        
        for(z in 1 : nTopics){
            p2_w_z[, z] = p2_w_z[, z] / sum(p2_w_z[, z])
            p2_d_z[, z] = p2_d_z[, z] / sum(p2_d_z[, z])   
        }
        p2_z = p2_z / sum(p2_z)
        
        p_w_z = p2_w_z
        p_d_z = p2_d_z
        p_z = p2_z
        
        # calculate log likelihook and compare with threshold
        likelihood = 0
        for(d in 1 : nImages){
            for(w in 1 : nWords){
                if(BOW[d, w] == 0)
                    next
                sum = 0
                for(z in 1 : nTopics)
                    sum = sum + p_z[z] * p_d_z[d, z] * p_w_z[w, z]
                p_dw[d, w] = sum
                likelihood = likelihood + log(sum) * BOW[d, w]
            }
        }
        
        delta = likelihood - lastLikelihood
        cat('Delta log likelihood: ', delta, '\n');
        print( Sys.time() );        
        
        if(delta < threshold) break
        lastLikelihood = likelihood
    }
    
    # return p(z|d)

    p_z_d = matrix(ncol = nTopics, nrow = nImages)
    for(d in 1 : nImages)
        for(z in 1 : nTopics)
            p_z_d[d, z] = p_d_z[d, z] * p_z[z]
   
    M = list(pWgZ=t(p_w_z), pDgZ=p_d_z, pZ=p_z, pZgD=p_z_d);
    return(M);    
}


main <- function() {
nTopics = 23
dataset = 'corel1000'

fk_bow = paste0(dataset, '-bow.csv');
fk_plsa = paste0(dataset, '-plsa.csv');

BOW = read.csv(fk_bow);
BOW = BOW[1:321, ];
BOW$target = NULL;

m = pLSA_2(BOW, nTopics, nrep = 2);
write.csv(m$pWgZ, file=paste0(dataset, '-plsa-pWgZ.csv'), row.names=F)
write.csv(m$pZgD, file=paste0(dataset, '-plsa-pZgD.csv'), row.names=F)
}

cat(rep('\n', 10) ); main();
