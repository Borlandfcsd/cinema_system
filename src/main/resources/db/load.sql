-- movies data
INSERT INTO movies(
	id, duration, title, poster, discription, director, stars, year, rating)
	VALUES (
    1,
    108,
    'Deadpool',
    'deadpool.jpg',
    'This is the origin story of former Special Forces operative turned mercenary Wade Wilson, who after being subjected to a rogue experiment that leaves him with accelerated healing powers, adopts the alter ego Deadpool. Armed with his new abilities and a dark, twisted sense of humor, Deadpool hunts down the man who nearly destroyed his life.',
    'Tim Miller',
    'Ryan Reynolds, Morena Baccarin, T.J. Miller',
    2016,
    '8,0'
	);
INSERT INTO movies(
	id, duration, title, poster, discription, director, stars, year, rating, part)
	VALUES (
    2,
    178,
    'The Lord of the Rings',
    'thelordoftherings1.jpg',
    'An ancient Ring thought lost for centuries has been found, and through a strange twist in fate has been given to a small Hobbit named Frodo. When Gandalf discovers the Ring is in fact the One Ring of the Dark Lord Sauron, Frodo must make an epic quest to the Cracks of Doom in order to destroy it! However he does not go alone. He is joined by Gandalf, Legolas the elf, Gimli the Dwarf, Aragorn, Boromir and his three Hobbit friends Merry, Pippin and Samwise. Through mountains, snow, darkness, forests, rivers and plains, facing evil and danger at every corner the Fellowship of the Ring must go. Their quest to destroy the One Ring is the only hope for the end of the Dark Lords reign!',
    'Peter Jackson',
    'Elijah Wood, Ian McKellen, Orlando Bloom',
    2001,
    '8,8',
    'The Fellowship of the Ring'
	);
INSERT INTO movies(
	id, duration, title, poster, discription, director, stars, year, rating)
	VALUES (
    3,
    175,
    'The Godfather',
    'thegodfather.jpg',
    'When the aging head of a famous crime family decides to transfer his position to one of his subalterns, a series of unfortunate events start happening to the family, and a war begins between all the well-known families leading to insolence, deportation, murder and revenge, and ends with the favorable successor being finally chosen.',
    'Francis Ford Coppola',
    'Marlon Brando, Al Pacino, James Caan',
    1972,
    '9,2'
	);
INSERT INTO movies(
	id, duration, title, poster, discription, director, stars, year, rating)
	VALUES (
    4,
    139,
    'Fight Club',
    'fight-club.jpg',
    'A nameless first person narrator (Edward Norton) attends support groups in attempt to subdue his emotional state and relieve his insomniac state. When he meets Marla (Helena Bonham Carter), another fake attendee of support groups, his life seems to become a little more bearable. However when he associates himself with Tyler (Brad Pitt) he is dragged into an underground fight club and soap making scheme. Together the two men spiral out of control and engage in competitive rivalry for love and power. When the narrator is exposed to the hidden agenda of Tyler''s fight club, he must accept the awful truth that Tyler may not be who he says he is. ',
    'David Fincher',
    'Brad Pitt, Edward Norton, Meat Loaf',
    1999,
    '8,8'
	);
INSERT INTO movies(
	id, duration, title, poster, discription, director, stars, year, rating)
	VALUES (
    5,
    136,
    'The Matrix',
    'matrix.jpg',
    'Thomas A. Anderson is a man living two lives. By day he is an average computer programmer and by night a hacker known as Neo. Neo has always questioned his reality, but the truth is far beyond his imagination. Neo finds himself targeted by the police when he is contacted by Morpheus, a legendary computer hacker branded a terrorist by the government. Morpheus awakens Neo to the real world, a ravaged wasteland where most of humanity have been captured by a race of machines that live off of the humans'' body heat and electrochemical energy and who imprison their minds within an artificial reality known as the Matrix. As a rebel against the machines, Neo must return to the Matrix and confront the agents: super-powerful computer programs devoted to snuffing out Neo and the entire human rebellion.',
    'The Wachowski Brothers',
    'Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss',
    1999,
    '8,7'
	);

-- users data
INSERT INTO users(
	id, email, first_name, last_name, password)
	VALUES (2,'borlandfcsd@gmail.com','Viktor','Sapaiev','$2a$11$sBSwsgsi.edYBlT34u5gjeB0WhKhkNGQwxs.ZqktvYEtdResTLlVS');
INSERT INTO users(
	id, email, first_name, last_name, password)
	VALUES (146,'example@mail.com','User','Test','$2a$11$lObWErDzPcFNtv08c3oBluypswilD3rZ/saDzcuwCo7ux1JMs/XYu');

-- roles data
INSERT INTO roles(
	id, name)
	VALUES ('1','ROLE_USER');
INSERT INTO roles(
	id, name)
	VALUES ('2','ROLE_ADMIN');

-- user-roles data
INSERT INTO user_roles(
	user_id, role_id)
	VALUES (2,2);
INSERT INTO user_roles(
	user_id, role_id)
	VALUES (146,1);

-- sessions
INSERT INTO sessions(
	id, begin_date, movie_id)
	VALUES (207,'2018-08-07 20:00:00',5);
INSERT INTO sessions(
	id, begin_date, movie_id)
	VALUES (208,clock_timestamp(),1);