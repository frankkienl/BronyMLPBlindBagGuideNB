/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.fluffikens.pattern;

/**
 *
 * @author FrankkieNL
 */
public class NullPattern extends Pattern {

    @Override
    public String get(int nr) {
        return "??";
    }
    
}
