#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <time.h>

int gcd(int a, int b);
int small(int a, int b);

void main(){
	/*
	//����12
	int count=0;
	double a,b;
	for(a=0;a<=100;a++){
		for(b=0;b<=100;b++){
			if((a*a)+(b*b)<=10000 && b<=a ){	
				count++;
			}
		}
	}
	printf("Count : %d\n",count);
	*/
	
	//���� 13
	/*int n,i;
	int sum=0;
	int count=0;
	scanf("%d",&n);
	for(i=1;i<n+1;i++){
		int j=i;
		while(j>0){
			sum = sum+(j%10);
			j = j/10;
		}
		if(sum%7==0)
			count++;
		sum=0;
	}
	printf("count : %d \n",count);*/
	
	
	//����14
	/*int n,i,j;
	int result;
	scanf("%d",&n);
	for(i=0;i<n;i++){
		for(j=0;j<n;j++){
			if(gcd(i+1,j+1)==1)
				printf(" * ");
			else
				printf("   ");
			if(j==(n-1))
				printf("\n");
		}
	}*/
	/*
	����15
	int N,n,a,b;
	int result=0;
	int count=0;
	scanf("%d",&N);

	for(n=1;n<N+1;n++){
		for(a=1;(a*a*a)<n;a++){
			for(b=1;(b*b*b)<n;b++){
				result = (a*a*a)+(b*b*b);
				if(result==n){
					count++;
				}
				
			}

		}
		if(count>2){
			printf("result : %d\n",n);
		}
		count=0;
	}*/

	//����16
	//int N,T;
	//int step=0,count=0;
	//int x=0,y=0;
	//int ahead;
	//double result1=0,result2=0;
	//srand((unsigned)time(NULL));
	//printf("N, T? ");
	//scanf("%d %d", &N,&T);
	//while(count<T){
	//	printf("%dȸ��\n",count+1);
	//	x=0;y=0;step=0;
	//	while(abs(x)<N && abs(y)<N){
	//		ahead = rand()%4;
	//		
	//		//0~1 : North 1~2 : South 2~3 : West 3~4 East
	//		if(ahead==3){
	//			x++;
	//			step++;
	//		}
	//		else if(ahead==2){
	//			x--;
	//			step++;
	//		}
	//		else if(ahead==1){
	//			y--;
	//			step++;
	//		}
	//		else{
	//			y++;
	//			step++;
	//		}

	//		//printf("(%d,%d) rand : %d\n",x,y,ahead);
	//	}
	//	
	//	result1 = result1 + step;
	//	printf("step : %d\n",step);
	//	count++;
	//}
	//result2 = result1 / (double)T;
	//printf("�ݺ� Ƚ�� T : %d , ��� step : %0.1f\n",T,result2);


	//����17
	int count=0,count1=0,count2=0;
	int x=0,y=0;
	int q1=0,q2=0;
	int dice=0;
	double quest1=0, quest2=0;

	srand((unsigned)time(NULL));

	while(count<10000){
		count1=0;count2=0;
		while(dice<6){
			int result = rand()%6;
			if(result==0){
				count1++;
			}
			dice++;
		}
		if(count1>=1)
			q1++;
		dice=0;
		quest1 = (double)q1/10000*100;
		
		while(dice<12){
			int result = rand()%6;
			if(result==0){
				count2++;
			}
			dice++;
		}
		if(count2>=2)
			q2++;
		quest2 = (double)q2/10000 * 100;
		dice=0;
		count++;
	}

	printf("Q1 : %0.1f%%, Q2 : %0.1f%%\n",quest1,quest2);
	//����18
	//int T,i;
	//int count=0;
	//int Car, myChoice, chChoice, open;
	//int res1=0,res2=0;

	//printf("T? ");
	//scanf("%d",&T);
	//srand((unsigned)time(NULL));
	//while(count<T){
	//	Car = rand()%3;	//where is car?
	//	myChoice = rand()%3;	//my choice
	//	
	//	for(i=0;i<3;i++){
	//		if(i!=Car && i!=myChoice)
	//			open = i;
	//	}

	//	//chage
	//	for(i=0;i<3;i++){
	//		if(i!=open && i!=myChoice)
	//			chChoice = i;
	//	}
	//	if(chChoice==Car)
	//		res1++;
	//	//do not change
	//	if(myChoice==Car)
	//		res2++;

	//	count++;
	//}
	//printf("change : %0.1f %%, do not change : %0.1f%%\n",((double)res1/T)*100,((double)res2/T)*100);


}


int gcd(int a, int b){
	int i,gcd;
	for(i=1;i<small(a,b)+1;i++){
		if(a%i==0 && b%i==0)
			gcd=i;
	}
	return gcd;
}

int small(int a, int b){
	if(a<=b)
		return a;
	else
		return b;
}


