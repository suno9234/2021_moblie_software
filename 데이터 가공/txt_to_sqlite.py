import sqlite3

con = sqlite3.connect('C:\\Users\\ssh92\\AndroidStudioProjects\\Project\\데이터 가공\\temp.db')
cursor = con.cursor()
#cursor.execute("CREATE TABLE Word(Word text, Mean text, 과목 text, 학년 text)")
file_path = 'C:\\Users\\ssh92\\AndroidStudioProjects\\Project\\데이터 가공\\완성\\ele_1_mean.txt'
with open(file_path,'r',encoding='utf-8') as f:
    lines = f.readlines()
    for line in lines:
        theme = ''
        if '[슬기로운 생활]' in line:
            theme = '슬기로운 생활'
        con.execute("INSERT ")


con.commit()
con.close()