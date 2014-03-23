package nl.fluffikens.pony.waves;

import nl.fluffikens.pattern.NullPattern;
import nl.fluffikens.pony.Pony;

/**
 *
 * @author FrankkieNL
 */
public class Wave10 extends AbstractWave {
     public Wave10() {
        this.names = new String[]
        {
            "Trixie Lulamoon", "Green Jewel", "Sunny Rays",
            "Lotus Blossom", "Ribbon Wishes", "Snailsquirm",
            "Twilight Sparkle (Princess)", "Peachy Sweet II",
            "Junebug", "Sunshower Raindrops", "Gilda the Griffon",
            "Strawberry Sunrise", "Sassaflash", "Royal Riff",
            "Lemon Hearts", "Applejack", "Diamond Mint",
            "Flower Wishes", "Fluttershy II", "Mosely Orange",
            "Pinkie Pie", "Rainbow Swoop", "Rainbowshine", "Snipsy Snap"                
            
        };

        this.descriptions = new String[][]
        {
            {"02", "glitter"}, {"05", "glitter"}, {"07", "glitter"}, 
            {"08", "glitter"}, {"09", "glitter"}, {"10", "glitter"}, 
            {"12", "glitter"}, {"13", "glitter"}, {"17", "glitter"}, 
            {"19", "glitter"}, {"20", "glitter"}, {"21", "glitter"}, 
            {"22", "glitter"}, {"23", "glitter"}, {"24", "glitter"}, 
            {"??", "glitter"}, {"??", "glitter"}, {"??", "glitter"}, 
            {"??", "glitter"}, {"??", "glitter"}, {"??", "glitter"}, 
            {"??", "glitter"}, {"??", "glitter"}, {"??", "glitter"}, 
            {"??", "glitter"}, 
        };

        this.wave = 10;
        this.waveName = "" + this.wave;
        this.ponies = new Pony[names.length];
        
        this.patterns.add(new NullPattern());

        this.init();

    }
}
