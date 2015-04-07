#include <stdio.h>
#include <math.h>
void main(){
	/*
	//연습 8
	int a,b,i;
	int result=1;
	scanf("%d %d",&a,&b);
	for(i=0;i<b;i++){
		result = result * a;
	}

	printf("%d의 %d제곱 : %d\n",a,b,result);
	*/
	/*
	//연습 9
	int sum=0;
	int count=1;
	int tmp;
	while(1){
		scanf("%d",&tmp);
		if(count%2!=0){	//홀수
			sum = sum + tmp;
			count++;
		}
		else{
			sum = sum - tmp;
			count++;
		}

		if(sum==0){
			printf("입력 된 정수의 개수 : %d\n",count-1);
			break;
		}

	}
	*/
	/*
	//연습10
	int a,i;
	double result=0;
	double count=1;
	scanf("%d",&a);
	for(i=0;i<a;i++){
		if((i+1)%2!=0){
			result = result + 4/count;
			count = count+2;
		}
		else{
			result = result - 4/count;
			count = count+2;
		}
	}

	printf("n : %d  pi : %f\n",a,result);
	*/
	/*
	//연습11
	int isbn[9];
	int count=0;
	int result=0,i;
	while(count<9){
		scanf("%d",&isbn[count]);
		count++;
	}

	count=0;

	for(i=10;i>1;i--){
		result = result + i*isbn[count];
		count++;
	}

	for(i=0;i<10;i++){
		if((result+i)%11==0){
			printf("마지막 자리수 : %d\n",i);
			break;
		}
		if(i==9)
			printf("마지막 자리수 : x\n");
	}
	*/
	//연습12
	int x=101,y=101;
	int count=0;
	double a,b;
	for(a=0;a<x;a++){
		for(b=0;b<y;b++){
			if(sqrt((a*a)+(b*b))<=100 && b<=a ){	
				count++;
			}
		}
	}

	printf("Count : %d\n",count);
}