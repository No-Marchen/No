/*******************************************************

				201011622 No siwan
				chapter 2 Array
					0401~

				0410 check Q1,Q2,Q3

********************************************************/

#include <stdio.h>
#include <math.h>

void Q1();
void Q2();
void Q3();
void Q4();
void Q5();

void main(){
	//Q1();
	//Q2();
	//Q3();
	Q4();
	//Q5();
}

void Q1(){
	/*				Chapter2 연습 1				 */
	int arr[10];
	int i=0;
	int min1,min2;

	printf("Please Enter numbers\n");
	while(i<10){
		printf("%d : ",i+1);
		scanf("%d",&arr[i]);
		i++;
	}

	for(i=1;i<10;i++){
		if(i==1){
			if(arr[0]<=arr[1]){
				min1 = arr[0];
				min2 = arr[1];
			}
			else{
				min1 = arr[1];
				min2 = arr[0];
			}
		}
		else{
			//value <= min1		
			if(arr[i]<=min1){
				min2 = min1;
				min1 = arr[i];
			//min1 < value <= min2
			}
			else if(arr[i]<=min2)
				min2 = arr[i];
		}
	}
	
	for(i=0;i<10;i++)
		printf("%d ",arr[i]);
	
	printf("\nMinimum1 : %d, Minimum2 : %d\n",min1,min2);
}

void Q2(){
	/*				Chapter2 연습 2				 */
	int arr[10];
	int i=0;
	double temp=0;
	double SD;

	double avg=0;
	printf("Please Enter numbers\n");
	while(i<10){
		printf("%d : ",i+1);
		scanf("%d",&arr[i]);
		avg = avg+arr[i];
		i++;
	}

	avg = (double)avg/10;

	for(i=0;i<10;i++)
		temp = temp + ((arr[i]-avg)*(arr[i]-avg));
	
	SD = sqrt((0.1)*temp);
	printf("Avg : %0.1f  SD : %0.1f\n",avg,SD);

}

void Q3(){
	/*				Chapter2 연습 3				 */
	char * news[4] = {"북" , "동" , "서" ,"남"};
	int x1,y1,ahead=-1;
	int x2,y2;
	
		printf("현재 좌표와 이동할 방향(0:north, 1:east, : 2:west, 3:south) \n");
		while(!(ahead>-1 && ahead<4)){
			scanf("%d %d %d",&x1,&y1,&ahead);
			if(!(ahead>-1 && ahead<4))
				printf("방향 (0~3)\n");
		}

		switch(ahead){
			case 0:	//북
				y2 = y1+1;
				x2 = x1;
				break;
			case 1:	//동
				x2 = x1+1;
				y2 = y1;
				break;
			case 2:	//서
				x2 = x1-1;
				y2 = y1;
				break;
			case 3:	//남
				y2 = y1-1;
				x2 = x1;
				break;
		};

		printf("(%d,%d) -> (%d,%d) %s\n",x1,y1,x2,y2,news[ahead]);
	
}

void Q4(){
	/*				Chapter2 연습 4				 */
	int data[10];
	int n=0,i;
	int count=0;
	int value,findValue;
	printf("Enter Numbers : \n");
	
	while (count<10) {
		scanf("%d", &value);
		i=9;
		while (i>=0 && data[i]>value) {
			data[i+1] = data[i];
			i--;
			}
		data[i+1] = value;
		n++;
		count++;
	}

	for (i = 0; i < n; i++)
		printf("%d ", data[i]);
	/*
	printf("\nEnter positive integers\n");
	scanf("%d",&findValue);

	i = 0;
	while(data[i]!=findValue && i<n)
		i++;
	
	if(i==n)
		printf("No Match : %d\n",findValue);
	else{
		while(i>=0 && i<n){
			data[i] = data[i+1];
			i++;
		}
		n--;
	}

	for (i = 0; i < n; i++)
		printf("%d ", data[i]);

	printf(" n : %d ", n);*/
}
void Q5(){
	/*				Chapter2 연습 5				 */
	int numbers[100];
	int N=0,i=0;
	int count = 1, Max_count=0;
	int max = 1;
	int start=-1, first, end;
	int value;

	printf("Enter at most 100 positive integers and press Ctl-z.\n");
	while (scanf("%d", &value) != EOF) {		
		numbers[i] = value;
		i++;
		N++;
	}

	for (i = 1; i < N; i++) {
		if (numbers[i]==numbers[i-1]) {
			if(start == -1)
				start = i-1;
			count++;
		}
		else{
			if(count > Max_count){
				Max_count = count;
				first = start;
				end = i-1;
			}
			start = -1;
			count = 1;
		}
	}
	printf("%d(%d,%d) \n",Max_count,first,end);
}
