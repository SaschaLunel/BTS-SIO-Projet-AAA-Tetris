/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package events;


// Déclaration de l'interface EventListener
public interface EventListener {
    /**
     * Méthode appelée lorsqu'un événement est déclenché.
     *
     * @param eventName Le nom de l'événement.
     * @param data      Les données associées à l'événement (peut être null si pas de données).
     */
    void onEvent(String eventName, Object data);
}

