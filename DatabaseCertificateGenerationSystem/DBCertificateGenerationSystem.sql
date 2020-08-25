CREATE DATABASE DBCertificate_Generation_System
GO

USE DBCertificate_Generation_System
GO
-- Table Admin & Certificate Cell
CREATE TABLE Admin_CertificateCell
(
	ID varchar(10) PRIMARY KEY,
	[Name] nvarchar(40) not null,
	DOB date,
	Gender varchar(6) not null,
	[Address] nvarchar(110) not null,
	Phone varchar(15),
	Username varchar(15) not null,
	[Password] char(50) not null,
	[Role] varchar(20)
)
GO
-- Table Student
CREATE TABLE Students
(
	StudentID varchar(10) PRIMARY KEY,
	[Name] nvarchar(40) not null,
	DOB date,
	Gender varchar(6) not null,
	[Address] nvarchar(120) not null,
	Phone varchar(15),
	Username varchar(15) not null,
	[Password] varchar(50) not null

)
GO
-- Table Teachers
CREATE TABLE Teachers
(
	TeacherID varchar(10) PRIMARY KEY,
	[Name] nvarchar(40) not null,
	DOB date,
	Gender varchar(6) not null,
	[Address] nvarchar(120) not null,
	Phone varchar(15),
	AcademicLevel varchar(100) not null,
	Technique varchar(100) not null
)
GO

-- Table Grade
CREATE TABLE Grades
(
	GradeID varchar(20) PRIMARY KEY,
	[GradeName] nvarchar(15) not null,
	[Max] int,
	[Min] int
)
GO
-- Table Course
CREATE TABLE Course
(
	CourseID varchar(10) PRIMARY KEY,
	[CourseName] nvarchar(20) not null
)
GO
-- Table Class
CREATE TABLE Class
(
	ClassID varchar(10) PRIMARY KEY,
	[ClassName] nvarchar(15) not null,
	StartDate date,
	EndDate date,
	CourseID varchar(10) not null REFERENCES Course(CourseID)
)
GO
-- Table Subject
CREATE TABLE Subjects
(
	SubjectID varchar(10) PRIMARY KEY,
	CourseID varchar(10) not null REFERENCES Course(CourseID),
	[SubjectName] nvarchar(50) not null,
	Fee int not null,
	NumOfLesson int not null
	
)
GO
-- Table Registry
CREATE TABLE Registry
(
	CourseID varchar(10) REFERENCES Course(CourseID),
	StudentID varchar(10) REFERENCES Students(StudentID),
	RegisDate date,
	Payments varchar(6),
	AverageMark int,
	[Status] varchar(40)


	PRIMARY KEY(CourseID,StudentID)
)
GO
-- TABLE Exam
CREATE TABLE Exam
(
	StudentID varchar(10) REFERENCES Students(StudentID),
	SubjectID varchar(10) REFERENCES Subjects(SubjectID),
	Mark int not null

	PRIMARY KEY(StudentID,SubjectID)
)
GO
CREATE TABLE ClassList
(
	StudentID varchar(10) REFERENCES Students(StudentID),
	ClassID varchar(10) REFERENCES Class(ClassID),
	-- CHỖ NÀY CẦN THÊM MỘT THUỘC TÍNH GÌ ĐÓ

	PRIMARY KEY(StudentID,ClassID)
)
GO
-- TABLE Teaching
CREATE TABLE Teaching
(
	ClassID varchar(10) REFERENCES Class(ClassID),
	TeacherID varchar(10) REFERENCES Teachers(TeacherID),
	SubjectID varchar(10) REFERENCES Subjects(SubjectID)

	PRIMARY KEY(ClassID,TeacherID,SubjectID)
)
GO
-- TABLE Certificate
CREATE TABLE Certificate
(
	CertificateID varchar(10) PRIMARY KEY,
	CertificateName nvarchar(30) not null,
	StudentID varchar(10) not null REFERENCES Students(StudentID),
	GradeID varchar(20) not null REFERENCES Grades(GradeID),
	CourseID varchar(10) not null REFERENCES Course(CourseID)
)
GO

-- PROCEDURE ID Students
CREATE PROC sp_Student_identityID
AS
BEGIN
DECLARE @ID VARCHAR(20)
DECLARE @max INT
SELECT @max=COUNT(StudentID)+1 FROM Students WHERE StudentID LIKE 'STU'
SET @ID= 'STU' + RIGHT('00' + CAST( @max AS varchar(10)),10)
WHILE(EXISTS(SELECT StudentID FROM Students WHERE StudentID=@ID))
BEGIN
	SET @max=@max+1
	SET @ID='STU'+ RIGHT('00' + CAST( @max AS varchar(10)),10)
END
SELECT @ID
END
GO

-- PROCEDURE ID Teachers
CREATE PROC sp_Teacher_identityID
AS
BEGIN
DECLARE @ID VARCHAR(20)
DECLARE @max INT
SELECT @max=COUNT(TeacherID)+1 FROM Teachers WHERE TeacherID LIKE 'TCH'
SET @ID= 'TCH' + RIGHT('00' + CAST( @max AS varchar(10)),10)
WHILE(EXISTS(SELECT TeacherID FROM Teachers WHERE TeacherID=@ID))
BEGIN
	SET @max=@max+1
	SET @ID='TCH'+ RIGHT('00' + CAST( @max AS varchar(10)),10)
END
SELECT @ID
END
GO

-- PROCEDURE ID Admin_CertificateCell
CREATE PROC sp_Admin_CertificateCell_identityID
AS
BEGIN
DECLARE @AdID VARCHAR(20)
DECLARE @max INT
SELECT @max=COUNT(ID)+1 FROM Admin_CertificateCell WHERE ID LIKE 'MA'
SET @AdID= 'MA' + RIGHT('00' + CAST( @max AS varchar(10)),10)
WHILE(EXISTS(SELECT ID FROM Admin_CertificateCell WHERE ID=@AdID))
BEGIN
	SET @max=@max+1
	SET @AdID='MA'+ RIGHT('00' + CAST( @max AS varchar(10)),10)
END
SELECT @AdID
END
GO
-- PROCEDURE ID Class
CREATE PROC sp_Class_identityID
AS
BEGIN
DECLARE @ID VARCHAR(20)
DECLARE @max INT
SELECT @max=COUNT(ClassID)+1 FROM Class WHERE ClassID LIKE 'CLS'
SET @ID= 'CLS' + RIGHT('00' + CAST( @max AS varchar(10)),10)
WHILE(EXISTS(SELECT ClassID FROM Class WHERE ClassID=@ID))
BEGIN
	SET @max=@max+1
	SET @ID='CLS'+ RIGHT('00' + CAST( @max AS varchar(10)),10)
END
SELECT @ID
END
GO

-- PROCEDURE ID Grade
CREATE PROC sp_Grade_identityID
AS
BEGIN
DECLARE @ID VARCHAR(20)
DECLARE @max INT
SELECT @max=COUNT(GradeID)+1 FROM Grades WHERE GradeID LIKE 'GRD'
SET @ID= 'GRD' + RIGHT('00' + CAST( @max AS varchar(10)),10)
WHILE(EXISTS(SELECT GradeID FROM Grades WHERE GradeID=@ID))
BEGIN
	SET @max=@max+1
	SET @ID='GRD'+ RIGHT('00' + CAST( @max AS varchar(10)),10)
END
SELECT @ID
END
GO

-- PROCEDURE ID Course
CREATE PROC sp_Course_identityID
AS
BEGIN
DECLARE @ID VARCHAR(20)
DECLARE @max INT
SELECT @max=COUNT(CourseID)+1 FROM Course WHERE CourseID LIKE 'COU'
SET @ID= 'COU' + RIGHT('00' + CAST( @max AS varchar(10)),10)
WHILE(EXISTS(SELECT CourseID FROM Course WHERE CourseID=@ID))
BEGIN
	SET @max=@max+1
	SET @ID='COU'+ RIGHT('00' + CAST( @max AS varchar(10)),10)
END
SELECT @ID
END
GO

-- PROCEDURE ID Subject
CREATE PROC sp_Subject_identityID
AS
BEGIN
DECLARE @ID VARCHAR(20)
DECLARE @max INT
SELECT @max=COUNT(SubjectID)+1 FROM Subjects WHERE SubjectID LIKE 'SUB'
SET @ID= 'SUB' + RIGHT('00' + CAST( @max AS varchar(10)),10)
WHILE(EXISTS(SELECT SubjectID FROM Subjects WHERE SubjectID=@ID))
BEGIN
	SET @max=@max+1
	SET @ID='SUB'+ RIGHT('00' + CAST( @max AS varchar(10)),10)
END
SELECT @ID
END
GO

-- PROCEDURE ID Certificate
CREATE PROC sp_Certificate_identityID
AS
BEGIN
DECLARE @ID VARCHAR(20)
DECLARE @max INT
SELECT @max=COUNT(CertificateID)+1 FROM [Certificate] WHERE CertificateID LIKE 'CET'
SET @ID= 'CET' + RIGHT('00' + CAST( @max AS varchar(10)),10)
WHILE(EXISTS(SELECT CertificateID FROM [Certificate] WHERE CertificateID=@ID))
BEGIN
	SET @max=@max+1
	SET @ID='CET'+ RIGHT('00' + CAST( @max AS varchar(10)),10)
END
SELECT @ID
END
GO

--SELECT s.[StudentID],[Name],[Username],cer.[CourseID],[CourseName],cer.[GradeID],[GradeName],[CertificateID],[CertificateName] FROM [Certificate] cer JOIN Students s ON cer.StudentID=s.StudentID JOIN Course cou ON cer.CourseID=cou.CourseID JOIN Grades g ON cer.GradeID = g.GradeID
--SELECT [SubjectID],[SubjectName],[Fee],[NumOfLesson],S.[CourseID],[CourseName] FROM Subjects S JOIN Course C ON S.CourseID=C.CourseID 

INSERT INTO Admin_CertificateCell VALUES('MA001','Pham Hoang Tam','2000-04-03','Male','New York','0327291328','admin','241541749143874487470050861112828356540','Administrator'),
										('MA002','Nguyen Truong Giang','2000-11-12','Male','Phu Quoc','1901237','certificatecell','241541749143874487470050861112828356540','Certificate Cell')
										
GO						
INSERT INTO Course VALUES('COU001','APTECH'),('COU002','ARENA')
GO
INSERT INTO Grades VALUES('GRD001','Distinction',100,75),('GRD002','A',74,60),('GRD003','B',59,50),('GRD004','C',49,0)
GO
INSERT INTO Class VALUES('CLS001','CP1896M04','2017-01-01','2020-01-01','COU001'),
						('CLS002','CP1896G05','2017-02-02','2020-02-02','COU001'),
						('CLS003','CP1896J06','2017-03-03','2020-03-03','COU002')
GO
INSERT INTO Students VALUES('STU001','Pham Hoang Trung','2001-04-03','Male','Tien Giang','0327291328','phamhoangtam','241541749143874487470050861112828356540')
							,('STU002','Trinh Tran Phuong Tuan','2001-02-02','Male','Ben Tre','09382498324','tranphuongtuan','241541749143874487470050861112828356540')
							,('STU003','Trinh Bao Khanh','2001-11-02','Male','Can Tho','09382498324','nguyenbaokhanh','241541749143874487470050861112828356540')
							,('STU004','Phan Thi My Tam','2001-11-11','Female','Da Nang','06797679794','phanthimytam','241541749143874487470050861112828356540')
							,('STU005','Tran Mot Em','2000-07-06','Male','Can Tho','03874567891','tranmotem','241541749143874487470050861112828356540')
							,('STU006','Nguyen Truong Giang','2000-05-03','Male','Tien Giang','03494567891','truonggiang','241541749143874487470050861112828356540')
							,('STU007','Nghiem Anh Tu','2000-08-05','Male','Hau Giang','03374567891','nghiemanhtu','241541749143874487470050861112828356540')
GO
INSERT INTO Registry VALUES	('COU001','STU001','2016-12-12','Unpaid',null,'Studying')
							,('COU001','STU002','2016-12-12','Unpaid',null,'Studying')
							,('COU001','STU003','2016-12-12','Paid',null,'Studying')
							,('COU001','STU004','2016-12-12','Unpaid',null,'Studying')
							,('COU002','STU005','2016-12-12','Paid',null,'Studying')
							,('COU002','STU006','2016-12-12','Unpaid',null,'Studying')
							,('COU002','STU007','2016-12-12','Paid',null,'Studying')
GO
INSERT INTO ClassList VALUES('STU001','CLS001')
							,('STU002','CLS001')
							,('STU003','CLS002')
							,('STU004','CLS002')
							,('STU005','CLS003')
							,('STU006','CLS003')
							,('STU007','CLS003')

GO
	INSERT INTO [dbo].[Subjects]
		 VALUES
			   ('SUB001'
			   ,'COU001'
			   ,'Application Development Fundamentals'
			   ,1200
			   ,40)
	GO
	INSERT INTO [dbo].[Subjects]
		 VALUES
			   ('SUB002'
			   ,'COU001'
			   ,'Application Programming'
			   ,1500
			   ,45)
	GO
	INSERT INTO [dbo].[Subjects]
		 VALUES
			   ('SUB003'
			   ,'COU001'
			   ,'Building Next Generation Websites'
			   ,1100
			   ,35)
	GO
	INSERT INTO [dbo].[Subjects]
		 VALUES
			   ('SUB004'
			   ,'COU002'
			   ,'Typography Design'
			   ,1600
			   ,40)
	GO
	INSERT INTO [dbo].[Subjects]
		 VALUES
			   ('SUB005'
			   ,'COU002'
			   ,'Concepts of Digital Film Making'
			   ,1500
			   ,42)
	GO
	INSERT INTO [dbo].[Subjects]
		 VALUES
			   ('SUB006'
			   ,'COU002'
			   ,'Digital Illustrations'
			   ,1200
			   ,40)
	GO
	
	INSERT INTO [dbo].[Subjects]
		 VALUES
			   ('SUB007'
			   ,'COU002'
			   ,'Concepts of Graphics and Illustrations'
			   ,1200
			   ,40)
	GO
	
INSERT INTO [dbo].[Teachers] VALUES
			('TCH001'
           ,'Le Thi Minh Loan'
           ,'1995-09-15'
           ,'Female'
           ,'Can Tho'
           ,'0986541357'
           ,'Master of Information Technology'
           ,'Information Technology')
GO
INSERT INTO [dbo].[Teachers] VALUES
			('TCH002'
           ,'Ong Thi My Linh'
           ,'1995-06-08'
           ,'Female'
           ,'Can Tho'
           ,'0357613573'
           ,'Master of Information Technology'
           ,'Information Technology')
GO
INSERT INTO [dbo].[Teachers] VALUES
			('TCH003'
           ,'Nguyen Vo Thong Thai'
           ,'1995-11-07'
           ,'Male'
           ,'Can Tho'
           ,'0222613573'
           ,'Master of Information Technology'
           ,'Information Technology')
GO
INSERT INTO [dbo].[Teachers] VALUES
			('TCH004'
           ,'Nguyen Trung Kien'
           ,'1995-07-12'
           ,'Male'
           ,'Can Tho'
           ,'0333613573'
           ,'Master of Information Technology'
           ,'Information Technology')
GO
INSERT INTO [dbo].[Teachers] VALUES
			('TCH005'
           ,'Le Phuoc Trung'
           ,'1996-08-12'
           ,'Male'
           ,'Can Tho'
           ,'0555613573'
           ,'Master of Information Technology'
           ,'Information Technology')
GO
INSERT INTO [dbo].[Teaching] VALUES
           ('CLS001'
           ,'TCH001'
           ,'SUB001')
GO
INSERT INTO [dbo].[Teaching] VALUES
           ('CLS001'
           ,'TCH002'
           ,'SUB002')
GO
INSERT INTO [dbo].[Teaching] VALUES
           ('CLS001'
           ,'TCH003'
           ,'SUB003')
GO
INSERT INTO [dbo].[Teaching] VALUES
           ('CLS002'
           ,'TCH001'
           ,'SUB001')
GO
INSERT INTO [dbo].[Teaching] VALUES
           ('CLS002'
           ,'TCH002'
           ,'SUB002')
GO
INSERT INTO [dbo].[Teaching] VALUES
           ('CLS002'
           ,'TCH002'
           ,'SUB003')
GO
INSERT INTO [dbo].[Exam]     
     VALUES
           ('STU001'
           ,'SUB001'
           ,90)
GO

INSERT INTO [dbo].[Exam]     
     VALUES
           ('STU001'
           ,'SUB002'
           ,60)
GO
INSERT INTO [dbo].[Exam]     
     VALUES
           ('STU001'
           ,'SUB003'
           ,70)
GO