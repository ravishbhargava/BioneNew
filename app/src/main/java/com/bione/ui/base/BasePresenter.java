package com.bione.ui.base;

/**
 * Developer: Bione
 */
public interface BasePresenter {

    /**
     * Indicates when the view has attached ( created )
     */
    void onAttach();

    /**
     * Indicates when the view has detached ( destroyed )
     */
    void onDetach();

    /**
     * Parses the message id from the throwable
     *
     * @param throwable the cause of network failure
     * @return the parsed message id
     */
    int parseThrowableMessage(Throwable throwable);
}
