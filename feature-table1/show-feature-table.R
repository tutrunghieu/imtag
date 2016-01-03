rm(list=ls());
set.seed(123);
# setwd('C:/Users/henrytu/Desktop/datasets');

main <- function()
{
cat("------Loading the big table\n");
df <- read.table('imtag-feature-table-14k.txt', sep="|");
print( dim(df) );

cat("------How many dataset do we have?\n");
U <- unique(df$V1);
print(U);

cat("------Selecting one dataset\n");
df_mit <- df[df$V1=='mit-2k', ];
# write.csv(df_mit, 'mit-feature-table-2k.txt', row.names = FALSE);
print( dim(df_mit) );
}

cat(rep("\n", 10)); main();
