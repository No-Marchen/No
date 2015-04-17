/*******************************************************

				201011622 No siwan
				chapter 2 Array
					0401~

				0410 check Q1,Q2,Q3
				0415 check Q4 to Q9
				0417 check Q10 to Q12

********************************************************/

#include <stdio.h>
#include <math.h>

void swap(int *a, int *b);
void Q1();	//find min1 and min2
void Q2();	//find SD
void Q3();	//NEWS go for
void Q4();	//circular right shift
void Q5();	//no exist empty room in array 
void Q6();	//longest plain
void Q7();	//Local Maxima
void Q8();	//left-to-right minima
void Q9();	//inverse permutation
void Q10();	//duplicate
void Q11();
void Q12();

/* Midterm */

void main(){
	//Q1();
	//Q2();
	//Q3();
	//Q4();
	//Q5();
	//Q6();
	//Q7();
	//Q8();
	//Q9();
	//Q10();
	//Q11();
	Q12();
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
	int n=0,i=0;
	int count=0;
	int value;
	int temp;
	printf("Enter Numbers : \n");
	
	while (count<10) {
		scanf("%d", &value);
		data[i++] = value;
		n++;
		count++;
	}
	printf("before shift : ");
	for(i=0;i<n;i++)
		printf("%d ", data[i]);

	temp = data[n-1];	//last data

	for (i = n-1; i > 0; i--){
		data[i] = data[i-1];
	}

	data[0] = temp;
	printf("\n after shift : ");
	for(i=0;i<n;i++)
		printf("%d ", data[i]);

}


void Q5(){
	/*				Chapter2 연습 5				 */
	int data[100];
	int n=0,i;
	int count=0;
	int value,findValue;
	printf("Enter Numbers : \n");
	
	while (scanf("%d", &value) != EOF) {
		i=n-1;
		while (i>=0 && data[i]>value) {
			data[i+1] = data[i];
			i--;
			}
		data[i+1] = value;
		n++;
		count++;
	}
	printf("N = %d , Array = ",n);
	for (i = 0; i < n; i++)
		printf("%d ", data[i]);
	
	printf("\nEnter number\n");
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

	printf("N = %d , Array = ", n);
	for (i = 0; i < n; i++)
		printf("%d ", data[i]);

	
}

void Q6(){
	/*				Chapter2 연습 6				 */
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

void Q7(){
	/*				Chapter2 연습 7				 */
	int arr[100];
	int i=0,n=0;
	int count=0;
	int value;

	printf("Please Enter numbers\n");
	while (scanf("%d", &value) != EOF) {		
		arr[i++] = value;
		n++;
	}

	for(i=0;i<n;i++){
		if(i==0){	//first
			if(arr[i]>=arr[i+1])
				count++;
		}
		else if(i==(n-1)){	//last
			if(arr[i]>=arr[i-1])
				count++;
		}
		else{
			if(arr[i]>=arr[i+1] && arr[i]>= arr[i-1])
				count++;
		}
	}

	printf("Local Maxima : %d\n",count);
}

void Q8(){
	/*				Chapter2 연습 8				 */
	int arr[100];
	int i=0,n;
	int count=0;
	int value,std;

	printf("Please Enter N(Max 100)\n");
	scanf("%d",&n);
	
	printf("Please Enter numbers N : %d \n",n);
	while (count < n) {
		scanf("%d",&value);
		arr[i++] = value;
		count++;
	}

	std = arr[0];

	count = 1;

	for(i=1;i<n;i++){
		if(arr[i] <= std)
			count++;
	}
	printf("left-to-right minima : %d \n",count);

}

void Q9(){
	/*				Chapter2 연습 9				 */
	int arr_A[100];
	int arr_B[100];
	int i=0,n;
	int count=0;
	int value,std;

	printf("Please Enter N(Max 100)\n");
	scanf("%d",&n);
	
	printf("Please Enter numbers : 0~%d \n",n-1);
	while (count < n) {
		scanf("%d",&value);
		arr_A[i++] = value;
		count++;
	}

	printf("Original permutation : ");
	for(i=0;i<n;i++)
		printf("%d ",arr_A[i]);

	for(i=0;i<n;i++)
		arr_B[arr_A[i]] = i; 
	
	printf("\nInverse permutation : ");
	for(i=0;i<n;i++)
		printf("%d ",arr_B[i]);
	printf("\n");
}

void Q10(){
	/*				Chapter2 연습 10				 */
	int arr[100];
	int i=0,j,n;
	int count=0;
	int value;
	int dup = 0;

	printf("Please Enter N(Max 100)\n");
	scanf("%d",&n);
	
	printf("Please Enter numbers : %d \n",n);
	while (count < n) {
		scanf("%d",&value);
		arr[i++] = value;
		count++;
	}

	for(i=n-1;i>0;i--){
		for(j=0;j<i;j++){
			if(arr[j]==arr[j+1])
				dup = 1;
			if(arr[j]>arr[j+1])
				swap(&arr[j],&arr[j+1]);		
		}
	}

	switch(dup){
	case 0:
		printf("\nNO\n\n");
		break;
	case 1:
		printf("\nYES\n\n");
		break;
	}	
}

void swap(int *a, int *b){
	int temp;
	temp = *a;
	*a = *b;
	*b = temp;
}

void Q11(){
	int arr[1000];
	int state = 0;
	int n,i=0,j;

	while(state!=1){
		scanf("%d",&n);
		arr[i++] = n;
		for(j=0;j<i-1;j++){
			if(arr[j] == n)
				state = 1;
		}
	} 

	printf("Count : %d\n",i);
}

void Q12(){
	int arr[100];
	int i=0,j,n;
	int count=0,count2=0;
	int value,k;

	printf("Please Enter N(Max 100)\n");
	scanf("%d",&n);
	
	printf("Please Enter numbers : %d \n",n);
	while (count < n) {
		scanf("%d",&value);
		arr[i++] = value;
		count++;
	}

	printf("Please Enter K\n");
	scanf("%d",&k);

	for(i=0;i<n;i++){
		for(j=i;j<n;j++){
			if(arr[i]+arr[j] == k){
				count2++;
			}
		}
	}

	printf("K : %d Count : %d\n",k,count2);
}