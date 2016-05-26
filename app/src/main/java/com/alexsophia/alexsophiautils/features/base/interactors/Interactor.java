package com.alexsophia.alexsophiautils.features.base.interactors;


/**
 * This is the main interface of an interactor. Each interactor serves a specific use case.
 */
public interface Interactor {

    /**
     * This is the main method that starts an interactor. It will make sure that the interactor operation is done on a
     * background thread.
     */
    void execute();

    /**
     * This is a special method that cancel an interactor. It will expose a "cancel" method for control the running
     * background thread beside execute() function. In the interactor implement, you need finish your working, such
     * as jump out the loop etc., and then calling "super.cancel()" for the thread recycle.
     */
    void cancel();
}
