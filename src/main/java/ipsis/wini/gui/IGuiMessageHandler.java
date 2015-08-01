package ipsis.wini.gui;

import ipsis.wini.network.message.MessageGuiWidget;

/**
 * Implement on containers that can handle gui messages
 */
public interface IGuiMessageHandler {

    void handleGuiWidget(MessageGuiWidget message);
}
