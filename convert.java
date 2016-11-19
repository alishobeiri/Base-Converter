package conv;

    import java.util.Arrays;

    public class tester {
            public static void main(String[] args) {

    class Number{
        
        
        public short[] arraySizer(short[] array,int size){ //Takes a specified array and resizes it to desired dimensions 
            short[] sizedArray = new short[size]; //Assigns a new with desired size
            System.arraycopy(array, 0, sizedArray, 0, size); //Copies over elements from first array to second
            return sizedArray; //Returns sized array
        }

        public short[] trimmer(short[] arrayOne) { //Takes array and trims extra zeroes from front
            int size=0;
            for(int i=arrayOne.length-1;i>0;i--){ //Iterates from the end of the array to the start
                if(arrayOne[i]==0){ //It counts the number of zeroes in front of the array 'insignificant zeroes'
                    size++;
                } else {
                   break;
                }
            }
            short[] trimmed = new short[arrayOne.length-size]; //Intializes an array with the desired size
            System.arraycopy(arrayOne, 0, trimmed, 0, trimmed.length); //Copies desired values to new trimmed array
            return trimmed;
        }
        

        public short[] addition(short[] arrayOne, short[] arrayTwo, int base){ //Takes two arrays and returns their sum
            int size=0;
            if(arrayOne.length==arrayTwo.length){ //If the size of both arrays is equal it makes each of them one unit longer in order to account for potential carry
                arrayOne=arraySizer(arrayOne,arrayOne.length+1);
                arrayTwo=arraySizer(arrayTwo,arrayTwo.length+1);
                size=arrayOne.length;
            }else if(arrayOne.length>arrayTwo.length){ //Assigns the size variable to the larger of the two arrays which is in this case the first array
                arrayTwo=arraySizer(arrayTwo,arrayOne.length);
                size=arrayOne.length;
            }else{
                arrayOne=arraySizer(arrayOne,arrayTwo.length); //Assigns the size variable to the larger of the two arrays which is in this case the second array
                size=arrayTwo.length;
            }
            short[] sum = new short[size+1]; //Makes an array called sum with a size == size but with one element added on just to be safe
            short carry=0;
            for (int i = 0;i<arrayOne.length;i++){ //Iterates through the arrays adding them and carries while only story significant values 
                sum[i]=(short)((arrayOne[i]+arrayTwo[i]+carry)%base);
                carry=(short)((arrayOne[i]+arrayTwo[i]+carry)/base);
            }
            return trimmer(sum);
        }


        public short[] multiplication(short[] array, int baseOne, int baseTwo){
            int size = array.length*3600;
            short[] product=new short[size];
            short[] temp=new short[size];
            int carry=0;
            short prod=0;
            int count=0;
            for(int i=0;i<array.length;i++){
                prod=(short)(array[i]*baseTwo+carry);
                carry=(short)(prod/baseOne);
                product[i]=(short)(prod%baseOne);
                count++;
            }
            int counter=count;
            if(carry>0){
                product[count++] = (short)(carry%baseOne);
                carry=carry/baseOne;
                while(carry/baseOne>0){
                    product[count++]=(short)(carry%baseOne);
                    carry=carry/baseOne;
                }
                product[count++]=(short)(carry);
            }
            return trimmer(product);
        }
        

        public short[] subtraction(short[] arrayOne, short[] arrayTwo, int base){ //Takes two arrays and returns their sum
            int size=0;
            if(arrayOne.length==arrayTwo.length){ //Again similar to addition makes sure both arrays are of equal length and assigns the bigger one as the variable size
                arrayOne=arraySizer(arrayOne,arrayOne.length);
                arrayTwo=arraySizer(arrayTwo,arrayTwo.length);
                size=arrayOne.length;
            }else if(arrayOne.length>arrayTwo.length){ 
                arrayTwo=arraySizer(arrayTwo,arrayOne.length);
                size=arrayOne.length;
            }else{
                arrayOne=arraySizer(arrayOne,arrayTwo.length);
                size=arrayTwo.length;
            }
            short[] temp=new short[arrayOne.length]; //Makes a temp array equal to the size of the largest array
            for(int i=0;i<arrayOne.length;i++){ //Makes sure there is no possiblity of recieving a negative result
                if(arrayOne[arrayOne.length-1-i]>arrayTwo[arrayOne.length-1-i]){
                    break;
                }else if(arrayOne[arrayOne.length-1-i]<arrayTwo[arrayOne.length-1-i]){
                    temp=arrayOne;
                    arrayOne=arrayTwo;
                    arrayTwo=temp;
                    break;
                }
            }
            short[] difference = new short[size]; //Calculates the difference
            short carry=0;
            for (int i = 0;i<arrayOne.length;i++) {
                if(arrayOne[i]<arrayTwo[i]){
                    arrayOne[i]=(short)(arrayOne[i]+base);
                    arrayOne[i+1]=(short)(arrayOne[i+1]-1);
                }
                difference[i]=(short)((arrayOne[i]-arrayTwo[i]+carry)%base);
                carry=(short)(((arrayOne[i]-arrayTwo[i])/base)-carry);
            }
            return trimmer(difference);
        }

        public short[] division(short[] array, int baseOne,int baseTwo){
            int size=0;
            array=trimmer(array); //Trims array making sure theirs no extra zeros
            size=array.length; //Assigns a value size to the length of array
            short[] quotient=new short[size];
            int carry=0;
                for(int i=size-1;i>=0;i--){ //Iterates from the end of array to the start of array
                    array[i]=(short)(array[i]+(carry*baseOne)); //Adds remaining carries if there any
                    int multiplicationFactor=array[i]/baseTwo; //Finds the number of times the base goes into the number of interest
                    array[i]=(short)(array[i]-(multiplicationFactor*baseTwo)); //Subtracts the value
                    quotient[i]=(short)(multiplicationFactor); //Sets the quotient equal to the number of times the base went into the number
                    carry=array[i]; //Sets carry equal to to the remaining value from the first operation
                    array[i]=0;//Clears the remaining value
                }
            return trimmer(quotient);
        }

        public int modulo(short[] array, int baseOne,int baseTwo){ //Basically same as division but returns remainder rather than quotient
            int size=0;
            array=trimmer(array);
            size=array.length;
            short[] quotient=new short[size];
            int carry=0;
                for(int i=size-1;i>=0;i--){
                    array[i]=(short)(array[i]+(carry*baseOne));
                    int multiplicationFactor=array[i]/baseTwo;
                    array[i]=(short)(array[i]-(multiplicationFactor*baseTwo));
                    quotient[i]=(short)(multiplicationFactor);
                    carry=array[i];
                    array[i]=0;
                }
            return carry; //Returns final carry, ie returns the remainder
        }

        public short[] arrayReverser(short[] array){ //Recieves array and reverses it
            for(int i = 0; i < array.length / 2; i++){ //Only iterates through half the array as that's all that is needed
                int temp = array[i];
                array[i] = array[array.length - i - 1];
                array[array.length - i - 1] = (short)temp;
            }
            return array;
        }

        public short[] decimalConvertor(short[] array,int baseOne, int baseTwo){ //Converts fractional values that are given and gives back an array
            int size=array.length;
            int resultSearch=(int)(Math.pow(baseOne, size));
            String[] matches=new String[resultSearch]; //Intializes an array with the size found above in order to store values
            short[] result=new short[resultSearch];//An array that will store the current value being searched for
            String current=" ";
            Arrays.fill(matches, " "); //Fills string array with empty strings to avoid NullPointerException
            int count=0;
            int index=0;
            boolean test=false;
            outerloop:
            for(int i=0;i<resultSearch;i++){ //Will iterate up to the maximum amount of permutations
                count++;
                array=multiplication(array,baseOne,baseTwo); //Stores array as the result of the multiplication by the desired base
                if(array.length>size){ //If the returned value is bigger than the initial value the first number is stored as part of the answer and the remaining 'fractional' digits are saved in matches
                    result[i]=array[size];
                    array[size]=0;
                    array=trimmer(array);
                    current=Arrays.toString(array); //Saves current fractional element to string
                }else{
                    result[i]=0; 
                    current=Arrays.toString(array);
                }
                for(int j=0;j<resultSearch;j++){
                    if(matches[j].equals(current)){//Searches through previous entries and breaks loop if any are found
                        test=true;
                    }
                }
                matches[i]=current;
                if(test==true){
                    break outerloop;
                }
            }
            int counter=0; //Will be used to count up
            int firstPosition=0; //Will store the first time a match is seen
            int secondPosition=0; //Will store the second time a match is seen
            for(int i=0;i<matches.length;i++){ 
                if(matches[i].equals(current)){
                    firstPosition=i;
                    break;
                }
            }
            for(int i=0;i<matches.length;i++){
                if(matches[i].equals(current)){
                    counter++;
                }
                if(counter==2){
                    secondPosition=i;
                    break;
                }
            }
            int repeating=Math.abs(secondPosition-firstPosition);
            short[] Repeating=new short[repeating];
            result=arraySizer(result,count);
            if(firstPosition<secondPosition){
                Repeating=Arrays.copyOfRange(result, firstPosition+1, secondPosition+1);
                nonRepeating(result,firstPosition);
            }else{
                Repeating=Arrays.copyOfRange(result, secondPosition+1, firstPosition+1);
                nonRepeating(result,firstPosition);
            }
            return (Repeating);
        }

        // declare a global array
        public short[] nonRepeatingGlobal= new short[14875642];
        
        public void nonRepeating(short[] result,int firstPosition){
            nonRepeatingGlobal=arrayReverser(Arrays.copyOfRange(result,0,firstPosition+1));
        }
        
        public short[] integerConvertor(short[] array, int baseOne, int baseTwo){
            short[] convertedArray=new short[147483647]; //Same reasoning as above, this size is because I cannot seem to make Math.pow work for big numbers
            int sum=arrayValue(array); //Returns the sum of the array
            int count=0; //Will iterate through the array
            while(sum>0){ //As long as the sum of the array is above is zero
                convertedArray[count]=(short)(modulo(array,baseOne,baseTwo)); //The remainder is stored as an input in the array
                count++; //Iterates up the array by one
                array=division(array,baseOne,baseTwo); //Divides array, basically reducing it's value
                sum=arrayValue(array); //Reassigns the value of sum
            }
            return trimmer(convertedArray); //Returns the converted array
        }

        public int arrayValue(short[] array){ //Sums up every element in the array and returns the value
            int sum=0;
            for(int i=0;i<array.length;i++){
                    sum+=array[i];
            }   
            return sum;
        }
        
        public Number convert(Number A, short Base) {
            Number B=new Number();
            int baseOne=A.Base;
            int baseTwo=Base;
            short[] integerPart=new short[A.Int.length],decimalPart=new short[A.Rep.length];
            integerPart=A.Int;
            integerPart=trimmer(integerPart);
            decimalPart=A.NonRep;
            B.Rep=decimalConvertor(decimalPart,baseOne,baseTwo);
            B.Int=integerConvertor(integerPart,baseOne,baseTwo);
            B.NonRep=nonRepeatingGlobal;
            B.Base=Base;
            return B;
        }

        public void printShortArray(short[] S) {
            for (int i = S.length-1; i>=0; i--) {
                System.out.print(S[i]);
            }
        }
        
        public void printNumber(Number N) {
                System.out.print("(");
                N.printShortArray(N.Int);
                System.out.print(".");
                N.printShortArray(N.NonRep);
                System.out.print("{");
                N.printShortArray(N.Rep);
                System.out.print("})_");
                System.out.println(N.Base);
            }
            short Base; short[] Int,NonRep,Rep;
    };

    Number N1=new Number() ;
            N1.Base=2; N1.Int=new short[6]; N1.NonRep=new short[3];
            N1.Int[1]=0; N1.Int[0]=0;
            N1.NonRep[0]=1;
            N1.Rep=new short[0];
            N1.printNumber(N1);
            Number N2=new Number() ;
            short R=10;
            N2=N1.convert(N1,R);
            N2.printNumber(N2);
        }

    }