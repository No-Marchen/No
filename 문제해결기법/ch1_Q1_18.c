#include <stdio.h>
#include <math.h>
#include <time.h>
#include <stdlib.h>

#define STEP 5
#define MIN 0
#define MAX 100

void swap(int *a, int *b);
void Q1();
void Q2();
void Q3();
void Q4();
void Q5();
void Q6_7();
void Q8();
void Q9();
void Q10();
void Q11();
void Q12();
void Q13();
void Q14();
void Q15();
void Q16();
void Q17();
void Q18();

int gcd(int a, int b);
int small(int a, int b);


void main(){
	//Q1();
	//Q2();
	//Q3();
	//Q4();
	//Q5();
	//Q6_7();
	//Q8();
	//Q9();
	//Q10();
	//Q11();
	//Q12();
	//Q13();
	//Q14();
	//Q15();
	//Q16();
	//Q17();
	//Q18();	
}

void swap(int *a, int *b){
	int temp = *a;
	*a = *b;
	*b = temp;
}

void Q1(){
	/*				Chapter1 연습 1				 */
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
	
}
void Q2(){
	/*				Chapter1 연습 2				 */
	int a=0;
	
	for(a=MIN;a<=MAX;a+=STEP){
		double F = a*1.8+32;
		printf("섭씨 : %d 화씨 : %0.1f\n",a,F);
	}
}
void Q3(){
	/*				Chapter1 연습 3				 */
	int a,b,c;
	scanf("%d %d %d",&a,&b,&c);
	while(!(a<=b && b<=c)){
		if(a>b)
			swap(&a,&b);
		if(b>c)
			swap(&b,&c);
		}
	printf("%d %d %d\n",a,b,c);
	
}
void Q4(){
	/*				Chapter1 연습 4				 */
	int a,b,c;
	int result=0;
	scanf("%d %d %d",&a,&b,&c);

	if(a>b+c || b>a+c || c>a+b)
		printf("No\n");
	else
		printf("Yes\n");
}
void Q5(){
	/*				Chapter1 연습 5				 */
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
	
}
void Q6_7(){
	/*				Chapter1 연습 6,7				 */
	int i;
	int count6=0,count7=0;
	for(i=10;i<10000;i++){
		int result = i%100;
		if(result%2 == 0 || result%3 == 0){
			count6++;
		}
	}
	
	for(i=10000;i<99999;i++){
		int result = i/1000;
		if(result%3 == 0 || result%7 == 0){
			count7++;
		}
	}

	printf("연습 6 - Count : %d\n",count6);
	printf("연습 7 - Count : %d\n",count7);
}
void Q8(){
	/*				Chapter1 연습 8				 */
	int a,b,i;
	int result=1;
	scanf("%d %d",&a,&b);
	for(i=0;i<b;i++){
		result = result * a;
	}

	printf("%d의 %d제곱 : %d\n",a,b,result);
}
void Q9(){
	/*				Chapter1 연습 9				 */
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
}
void Q10(){
	/*				Chapter1 연습 10			 */
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
}
void Q11(){
	/*				Chapter1 연습 11			 */
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
}
void Q12(){
	/*				Chapter1 연습 12			 */
	int count=0;
	double a,b;
	for(a=0;a<=100;a++){
		for(b=0;b<=100;b++){
			if((a*a)+(b*b)<=1000 && b<=a ){	
				count++;
			}
		}
	}
	printf("Count : %d\n",count);
	
}
void Q13(){
	/*				Chapter1 연습 13			 */
	int n,i;
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
	printf("count : %d \n",count);
}
void Q14(){
	/*				Chapter1 연습 14			 */
	int n,i,j;
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
	}
}
void Q15(){
	/*				Chapter1 연습 15			 */
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
	}
}
void Q16(){
	/*				Chapter1 연습 16			 */
	int N,T;
	int step=0,count=0;
	int x=0,y=0;
	int ahead;
	double result1=0,result2=0;
	srand((unsigned)time(NULL));
	printf("N, T? ");
	scanf("%d %d", &N,&T);
	while(count<T){
		printf("%d회차\n",count+1);
		x=0;y=0;step=0;
		while(abs(x)<N && abs(y)<N){
			ahead = rand()%4;
			
			//0~1 : North 1~2 : South 2~3 : West 3~4 East
			if(ahead==3){
				x++;
				step++;
			}
			else if(ahead==2){
				x--;
				step++;
			}
			else if(ahead==1){
				y--;
				step++;
			}
			else{
				y++;
				step++;
			}

			//printf("(%d,%d) rand : %d\n",x,y,ahead);
		}
		
		result1 = result1 + step;
		printf("step : %d\n",step);
		count++;
	}
	result2 = result1 / (double)T;
	printf("반복 횟수 T : %d , 평균 step : %0.1f\n",T,result2);
}
void Q17(){
	/*				Chapter1 연습 17			 */
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
}
void Q18(){
	/*				Chapter1 연습 18			 */
	int T,i;
	int count=0;
	int Car, myChoice, chChoice, open;
	int res1=0,res2=0;

	printf("T? ");
	scanf("%d",&T);
	srand((unsigned)time(NULL));
	while(count<T){
		Car = rand()%3;	//where is car?
		myChoice = rand()%3;	//my choice
		
		for(i=0;i<3;i++){
			if(i!=Car && i!=myChoice)
				open = i;
		}

		//chage
		for(i=0;i<3;i++){
			if(i!=open && i!=myChoice)
				chChoice = i;
		}
		if(chChoice==Car)
			res1++;
		//do not change
		if(myChoice==Car)
			res2++;

		count++;
	}
	printf("change : %0.1f %%, do not change : %0.1f%%\n",((double)res1/T)*100,((double)res2/T)*100);
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