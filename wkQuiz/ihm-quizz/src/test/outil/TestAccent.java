/*
 * TestLectureFichier.java                                    28 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package test.outil;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static outil.Accent.convertirAccent;

import java.util.HashMap;

/** 
 * Test unitaire de la classe
 * @author François de Saint Palais
 */
class TestAccent {

    /** Un caractère inconnu */
    private static final char CARACTERE_INCONNU = '\u0182';

    @Test
    void testConvertiAccent() {
        assertEquals('e', convertirAccent('é'));
        assertEquals(' ', convertirAccent(' '));
        assertEquals('?', convertirAccent('?'));
        assertEquals('?', convertirAccent(CARACTERE_INCONNU));
        assertEquals('?', convertirAccent('�'));
        assertEquals('=', convertirAccent('='));
        
        
        
        assertEquals("ete", convertirAccent("été"));
        assertEquals("eEe", convertirAccent("éÊé"));

        assertEquals("eE?", convertirAccent("éÊ" + CARACTERE_INCONNU));
        
        HashMap<String, String> donnee = new HashMap<String, String>();
        donnee.put("été", "chaud");
        HashMap<String, String> attendu = new HashMap<String, String>();
        attendu.put("été", "chaud");
        
        assertEquals(attendu, convertirAccent(donnee));
        
        donnee.clear();
        donnee.put("clé", "ééèàâ");
        attendu.clear();
        attendu.put("clé", "eeeaa");
        
        assertEquals(attendu, convertirAccent(donnee));
        
        donnee.clear();
        donnee.put("clé", "ééè^Gàâ" + CARACTERE_INCONNU);
        attendu.clear();
        attendu.put("clé", "eee^Gaa?");
        
        assertEquals(attendu, convertirAccent(donnee));
                
    }
}