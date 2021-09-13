/**
 * Package Rest
 */
package com.springangularbraderie.restcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springangularbraderie.model.Article;
import com.springangularbraderie.model.Panier;
import com.springangularbraderie.model.Account;
import com.springangularbraderie.service.ArticleService;
import com.springangularbraderie.service.PanierService;
import com.springangularbraderie.service.UserService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author JRSS
 * Restful Magasin
 */

@Slf4j
@RestController
@RequestMapping("/magasin")
@CrossOrigin(origins ="*")
public class MagasinRestController {

	@Autowired
	ArticleService hArticleService;

	@Autowired
	PanierService hPanierService;

	@Autowired
	UserService hUserService;

	/**
	 * Permet de retourner la liste des articles
	 * @return list Panier {@link List} {@link Article}
	 */
	@GetMapping(path="/getAllArticle", produces= "application/json")
	public List<Article> getAllArticle() {

		List<Article> lArticle = hArticleService.getAllArticle();

		return lArticle;
	}

	/**
	 * Permet de retourner un article specifique
	 * @param idArticle {@link Integer}
	 * @return Article {@link Article}
	 */
	@GetMapping(path="/getArticle", produces= "application/json")
	public Article getArticleById(int idArticle) {

		Article hArticle = hArticleService.getArticle(idArticle).get();

		log.info("Article ajouté : " + hArticle);

		return hArticle;
	}

	/**
	 * Sauvegarde la liste de panier selon un User
	 * @param p_lPanier {@link Optional} {@link Article}
	 * @return list Panier {@link Optional} {@link Article}
	 */
	@PostMapping(path="/savePanier", consumes= "application/json")
	public List<Panier> savePanier(@RequestBody List<Panier> p_lPanier) {

		// Suppression de la liste de panier du User dans la BDD
		hPanierService.deleteAll(p_lPanier.get(0).getUser().getIdUser());

		// Parcours la liste de panier pour l'insérer dans la bdd
		for (Panier panier : p_lPanier) {	
			Account p_user = panier.getUser();
			Article p_article= panier.getArticle();
			hPanierService.insertArticle(p_user, p_article, panier.getQuantite());
		}

		log.info("Panier sauvegardé : " + p_lPanier);	

		return p_lPanier;
	}

	/**
	 * Supprime la liste de panier appartenant à un User
	 * @param idUser {@link Integer}
	 */
	@DeleteMapping(path="/clear/{id}")
	public void deleteCaddie(@PathVariable("id") int idUser) {

		// Suppression de la liste de panier du User dans la BDD
		hPanierService.deleteAll(idUser);

		// Récupération de la liste de panier de la BDD (qui doit être vide)
		List<Panier> listeCaddie = hPanierService.getListArticle(idUser);

		log.info("Panier supprimé, taille du panier : " + listeCaddie.size());
	}


	/**
	 * Récupère une liste de Panier appartenant à un User
	 * @param idUser {@link Integer}
	 * @return list panier {@link List} {@link Panier}
	 */
	@GetMapping(path="/getListPanier", produces= "application/json")
	public List<Panier> restore(int idUser) {

		Account hUser = hUserService.findByIdUser(idUser).get();

		// Récupération de la liste de panier dans la BDD 
		List<Panier> listeCaddie = hPanierService.setPrixListPanier(hUser);	

		@SuppressWarnings("unused")
		Integer prixTotal = hPanierService.totalPanier(hUser.getIdUser());

		log.info("Panier récupéré - taille du panier : " + listeCaddie.size() + ", du user : " + hUser);

		return listeCaddie;
	}
}