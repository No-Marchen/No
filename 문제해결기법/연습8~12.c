#include <stdio.h>
#include <math.h>
void main(){
	/*
	//���� 8
	int a,b,i;
	int result=1;
	scanf("%d %d",&a,&b);
	for(i=0;i<b;i++){
		result = result * a;
	}

	printf("%d�� %d���� : %d\n",a,b,result);
	*/
	/*
	//���� 9
	int sum=0;
	int count=1;
	int tmp;
	while(1){
		scanf("%d",&tmp);
		if(count%2!=0){	//Ȧ��
			sum = sum + tmp;
			count++;
		}
		else{
			sum = sum - tmp;
			count++;
		}

		if(sum==0){
			printf("�Է� �� ������ ���� : %d\n",count-1);
			break;
		}

	}
	*/
	/*
	//����10
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
	//����11
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
			printf("������ �ڸ��� : %d\n",i);
			break;
		}
		if(i==9)
			printf("������ �ڸ��� : x\n");
	}
	*/
	//����12
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