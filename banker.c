#include<stdio.h>
int main()
{
    int n,m,i,j,k;
    int count=0,found;
    printf("enter no. of processes and resources:");
    scanf("%d %d",&n, &m);
    int max[n][m],allocated[n][m],need[n][m];
    int available[m],safeseq[n],finish[n];
    printf("enter max needs matrix:");
    for(i=0;i<n;i++)
    {
        for(j=0;j<m;j++)
        {
            scanf("%d",&max[i][j]);
        }
        printf("\n");
    }
    printf("enter allocated matrix:");
    for(i=0;i<n;i++)
    {
        for(j=0;j<m;j++)
        {
            scanf("%d",&allocated[i][j]);
        }
    }
    printf("enter available resources:");
    for(i=0;i<m;i++)
    {
        scanf("%d",&available[i]);
    }
        for(i=0;i<n;i++)
        {
            finish[i]=0;
        }
        for(i=0;i<n;i++){
            for(j=0;j<m;j++)
            {
                need[i][j]=max[i][j]-allocated[i][j];
            }
        }
    printf("enter need matrix:");
    for(i=0;i<n;i++)
    {
        for(j=0;j<m;j++)
        {
        printf("%d ",need[i][j]);
    }
    printf("\n");
}
    while(count<n)
    {
        found=0;
        for(i=0;i<n;i++)
        {
            if(finish[i]==0)
            {
                for(j=0;j<m;j++)
                {
                    if(need[i][j]>available[j]){
                    break;
                    }
                }
                    if(j==m)
                    {
                    for(k=0;k<m;k++)
                    {
                    available[k]+=allocated[i][k];
                    }
                    safeseq[count++]=i;
                    finish[i]=1;
                    found=1;
                    }
             }
        }
             if(found==0){
             printf("system is not in safe state\n");
             return 0;
             }
    }
                printf("safe sequence:");
                for(i=0;i<n;i++)
                {
                    printf("P%d ",safeseq[i]+1);
                }        
    return 0;
}