#include "stdio.h"


void swap(int *a, int *b);

void main(){
	/*int a,b,c;
	double x1,x2;
	printf("��� a�� �Է��Ͻÿ� : ");
	scanf("%d",&a);
	printf("��� b�� �Է��Ͻÿ� : ");
	scanf("%d",&b);
	printf("��� c�� �Է��Ͻÿ� : ");
	scanf("%d",&c);

	x1 = ((-b)+sqrt((b*b)-(4*a*c)))/(2*a);
	x2 = ((-b)-sqrt((b*b)-(4*a*c)))/(2*a);

	if(b*b-4*a*c<0)
		printf("NO ROOT\n");
	else
		printf("x1 = %0.2f, x2 = %0.2f\n",x1,x2);
	*/
	/*
	int a=0;
	for(a=MIN;a<=MAX;a+=STEP){
		double F = a*1.8+32;
		printf("%0.1f\n",F);
	}*/

	int a,b,c;
	scanf("%d %d %d",&a,&b,&c);
	while(!(a<b & b<c)){
	if(a>b)
		swap(&a,&b);
	if(b>c)
		swap(&b,&c);
	}
		
	
	

	printf("%d %d %d",a,b,c);
}

void swap(int *a, int *b){
	int temp;
	temp = *a;
	*a = *b;
	*b = temp;
}