package org.mox.spikes.domain.repository.exceptions;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class RepositoryException extends Exception {

    public RepositoryException(String s) {

        super(s);
    }

    public RepositoryException(String s, Throwable throwable) {

        super(s, throwable);
    }
}
