#include <stdio.h>
#include <math.h>

#define STEP 5
#define MIN 0
#define MAX 100

void swap(int *a, int *b);

void main(){
	/*
	//-- 연습 1 --
	int a,b,c;

	double x1, x2;

	printf("3개의 정수를 입력하시오\n");
	scanf("%d %d %d", &a,&b,&c);
	
	x1 = ((-b)+sqrt((b*b)-(4*a*c)))/(2*a);
	x2 = ((-b)-sqrt((b*b)-(4*a*c)))/(2*a);

	if(b*b-4*a*c<0)
		printf("실수근이 존재하지 않습니다\n");
	else
		printf("x1 = %0.1f, x2 = %0.1f\n",x1,x2);
	*/

	/*
	//-- 연습 2 --
	int a=0;
	
	for(a=MIN;a<=MAX;a+=STEP){
		double F = a*1.8+32;
		printf("섭씨 : %d 화씨 : %0.1f\n",a,F);
	}
	*/
	/*
	//-- 연습 3 --
	int a,b,c;
	scanf("%d %d %d",&a,&b,&c);
	while(!(a<b && b<c)){
		if(a>b)
			swap(&a,&b);
		if(b>c)
			swap(&b,&c);
		}
	printf("%d %d %d\n",a,b,c);
	*/

	/*
	//-- 연습 4 --
	int a,b,c;
	int result=0;
	scanf("%d %d %d",&a,&b,&c);

	if(a>b+c || b>a+c || c>a+b)
		printf("No\n");
	else
		printf("Yes\n");
	*/
	/*
	//-- 연습 5 --
	int a,b,c,d,e;
	int b1, b2, s1,s2;
	int medium;
	scanf("%d %d %d %d %d",&a,&b,&c,&d,&e);

	// 1
	if(a<=b){
		b1 = b;
		s1 = a;
	}
	else{
		b1 = a;
		s1 = b;
	}

	//2
	if(c<=d){
		b2 = d;
		s2 = c;
	}
	else{
		b2 = c;
		s2 = d;
	}
	//3
	if(b1<=b2){
		//4
		if(s2<=e){
			b2 = e;
		}
		else{
			b2 = s1;
			s2 = e;
		}
	}
	else{
		if(s1<=e){
			b1 = e;
		}
		else{
			b1 = s1;
			s1 = e;
		}
	}

	//5
	if(b1<=b2){
		//6
		if(b1<=s2)
			printf("Medium : %d\n",s2);
		else
			printf("Medium : %d\n",b1);
	}
	else{
		if(b2<=s1)
			printf("Medium : %d\n",s1);
		else
			printf("Medium : %d\n",b2);
	}
	*/
	//-- 연습 6,7 --
	int i;
	int count6=0,count7=0;
	for(i=10;i<10000;i++){
		int result = i%100;
		if(result%2 == 0 || result%3 == 0){
			count6++;
		}
	}
	
	for(i=10000;i<14000;i++){
		int result = i/1000;
		if(result%3 == 0 || result%7 == 0){
			count7++;
		}
	}

	printf("연습 6 - Count : %d\n",count6);
	printf("연습 7 - Count : %d\n",count7);
}
/*
void swap(int *a, int *b){
	int temp = *a;
	*a = *b;
	*b = temp;
}*/