import sqlite3

con = sqlite3.connect('C:\\Users\\ssh92\\AndroidStudioProjects\\Project\\데이터 가공\\temp.db')
cursor = con.cursor()

cursor.execute("CREATE TABLE Word(Word text, Mean text, 과목 text, 학년 text)")


con.commit()
con.close()