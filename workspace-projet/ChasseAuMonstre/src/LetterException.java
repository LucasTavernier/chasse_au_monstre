/**
 *	@author Arnaud MADOU
 *  @author Lucas TAVERNIER
 *  @author Tristan DESCHAMPS
 *	Projet Java 
 *	
 *	LetterException
 *
 *	Cette exception permet de gérer quand l'utilisateur veut écrire un nombre à la place d'une lettre
 */
public class LetterException extends RuntimeException {
	private static final long serialVersionUID = 8064188747926753507L;

	/**
	 * Créé une instance de l'exception
	 */
	public LetterException() {
	}

	/**
	 * @param message
	 */
	public LetterException(String message) {
		super(message);
	}


}
