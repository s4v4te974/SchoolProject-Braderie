DROP TABLE IF EXISTS T_ARTICLE_SPRING CASCADE;
DROP TABLE IF EXISTS T_USER_SPRING CASCADE;
DROP TABLE IF EXISTS T_PANIER_SPRING CASCADE;

CREATE TABLE T_ARTICLE_SPRING (
  id_article INT AUTO_INCREMENT  PRIMARY KEY,
  description VARCHAR(250) ,
  marque VARCHAR(250),
  prix INT
);

CREATE TABLE T_USER_SPRING (
  id_user INT AUTO_INCREMENT  PRIMARY KEY,
  login VARCHAR(250),
  pass VARCHAR(250),
  nb_Connexion VARCHAR(250),
  role VARCHAR(250)
);

CREATE TABLE T_PANIER_SPRING (
  id_panier INT AUTO_INCREMENT  PRIMARY KEY,
  quantite INT,
  id_user INT,
  id_article INT,
  
  CONSTRAINT fk_user
      FOREIGN KEY(id_user) 
	  REFERENCES T_USER_SPRING(id_user),
  CONSTRAINT fk_article
      FOREIGN KEY(id_article) 
	  REFERENCES T_ARTICLE_SPRING(id_article)
);