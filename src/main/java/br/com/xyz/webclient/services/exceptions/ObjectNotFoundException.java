package br.com.xyz.webclient.services.exceptions;

public class ObjectNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ObjectNotFoundException(String mensagem) {
        super(mensagem);
    }
    public ObjectNotFoundException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
