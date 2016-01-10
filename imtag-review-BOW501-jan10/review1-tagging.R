rm(list=ls());
set.seed(1997);
library(caret);

src = 'C:/Users/henrytu/Desktop/ImgTag-master/BOW/BOW - ColorAverage';
report = 'C:/Users/henrytu/Desktop/BOW501-svmlinear-jan10.txt';

load1 <- function(each) {
     print(each);
     df = read.csv(each);
     df$target = as.factor(df$target)

     S <- runif(nrow(df), 0, 1);
     L <- list(trainSet=df[S<0.7, ], testSet=df[S>=0.7, ]);
     print(dim(L$trainSet));
     print(dim(L$testSet));
     return(L);
}

print1 <- function(M, df) {
     y <- df$target;
     y1 = predict(M, newdata=df);
     print( table(y1, y) );
     print( sum(y1 != y) / length(y) );
}

main <- function(src) { 
   
  dataFrames = list.files(src, full.names = T);

  for(each in dataFrames) {
    L <- load1(each);
    M = train(target ~ ., data = L$trainSet, method = 'svmLinear');
    print1(M, L$trainSet);
    print1(M, L$testSet);
  }

}

cat( rep("\n", 10) ); sink(report); main(src); sink();
