file_path="C:\\Users\\ssh92\\OneDrive\\바탕 화면\\데이터 가공\\데이터원본.txt"

with open(file_path,'r',encoding='utf-8') as f:
    elementary_1 = open('elementary_1.txt','w',encoding='utf-8')
    elementary_3 = open('elementary_3.txt','w',encoding = 'utf-8')
    elementary_5 = open('elementary_5.txt','w',encoding = 'utf-8')
    middle = open('middle.txt','w',encoding = 'utf-8')
    high = open('high.txt','w',encoding = 'utf-8')

    lines = f.readlines()
    flag =0
    for line in lines:
        
        if '< 초등학교 > 언어 재료' in line:
            flag = 1
        elif '1, 2학년' in line:
            flag =1
        elif '3, 4학년' in line:
            flag =2
        elif '5, 6학년' in line:
            flag =3
        elif '< 중학교 > 언어 재료' in line:
            flag =4
        elif '< 고등학교 > 언어 재료' in line:
            flag =5
        
        if flag == 1:
            elementary_1.write(line)
        elif flag ==2:
            elementary_3.write(line)
        elif flag ==3:
            elementary_5.write(line) 
        elif flag ==4 :
            middle.write(line)
        elif flag ==5:
            high.write(line)
   
        

    elementary_1.close()
    elementary_3.close()
    elementary_5.close()
    middle.close()
    high.close()
