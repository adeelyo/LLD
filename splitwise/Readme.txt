The Tables were created manually
by the following commands

Create Table User
(userId integer auto_increment not null  primary key, userName varchar(30) unique, phoneNumber varchar(12) unique, password varchar(50))

create Table Expense
(ExpenseId integer auto_increment not null  primary key, description varchar(100), paidBy varchar(20), splitMethod varchar(30), createdOn datetime default CURRENT_DATE)

create Table Groups
(groupId integer auto_increment not null  primary key, title varchar(20), userId integer not null, createdOn datetime default current_timestamp, foreign key(userId) references User(userId))

create Table GroupMembers
(groupId integer, userId integer, foreign key(groupId) references Groups(groupId), foreign key(userId) references User(userId))

create Table UserExpense
(userId integer, expenseId integer, paid integer, owe integer, foreign key(userId) references User(userId), foreign key(expenseId) references Expense(ExpenseId))

create Table GroupExpense
(groupId integer, expenseId integer unique, foreign key(groupId) references Groups(groupId), foreign key(expenseId) references Expense(expenseId))
