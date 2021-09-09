/**
 * Package Service
 */
package com.springangularbraderie.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springangularbraderie.model.Article;
import com.springangularbraderie.repository.IAdminRep;
import com.springangularbraderie.repository.IArticleRep;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author JRSS
 * Classe Service de l'admin
 *
 */
@Service
@Data
@Slf4j
public class AdminService implements IAdminService {
	
	@Autowired
	IAdminRep adminRep;
	
	@Autowired
	ArticleService articleService;
	
	@Autowired
	IArticleRep articleRep;

	/**
	 * permet d'updater les attributs d'un article
	 * 
	 * @param p_idArticle {@link Article}
	 * @return article {@link Article}
	 */
	@Transactional
	public Article updateArticleAdmin(Article p_article) {
		
		// recupere l'article par son ID 
		Integer idArticle = p_article.getIdArticle();
		
		// recuperer 'article dans la BDD correspondant
		Article articleBDD = articleService.getArticle(idArticle).get();
		
		// on set les nouvelle definition 
		articleBDD.setDescription(p_article.getDescription());
		articleBDD.setMarque(p_article.getMarque());
		articleBDD.setPrix(p_article.getPrix());
		
		// on le sauve dans la BDD 
		adminRep.save(articleBDD);
		
		log.info("Article mise à jour dans la base de données");
		
		return articleBDD;
	}

	/**
	 * Permet de supprimer un article de la base de données
	 * @param idArticle {@link Integer}
	 */
	@Transactional
	public void removeArticleAdmin(Integer idArticle) {
		
		// on récupère notre article
		Article articleBDD = articleService.getArticle(idArticle).get(); 
		
		// on le supprime de la BDD
		articleRep.delete(articleBDD);
		
		log.info(" Article supprimé de la base de données");
	}
	
	/**
	 * Permet de persister un article 
	 * 
	 * @param p_Article {@link Article}
	 * @return article {@link Article}
	 */
	@Transactional
	public Article createArticleAdmin(Article p_Article) {
		
		Article articleCreate = new Article(p_Article.getDescription(), p_Article.getMarque(), p_Article.getPrix());
		
		adminRep.save(articleCreate);
		
		return articleCreate;
	}
}
