package nl.fluffikens.pony.waves;

import nl.fluffikens.pattern.AZPattern;
import nl.fluffikens.pony.Pony;

/**
 *
 * @author FrankkieNL
 */
public class Wave10 extends AbstractWave {

    public Wave10() {
        this.names = new String[]{
            "Applejack",
            "Trixie Lulamoon",
            "Fluttershy",
            "Pinkie Pie",
            "Green Jewel",
            "Rainbowshine",
            "Sunny Rays",
            "Lotus Blossom",
            "Ribbon Wishes",
            "Snailsquirm",
            "Diamond Mint",
            "Twilight Sparkle (Princess)",
            "Peachy Sweet",
            "Flower Wishes",
            "Snipsy Snap",
            "Mosely Orange",
            "June Bug",
            "Rainbow Swoop",
            "Sunshower Raindrops",
            "Gilda The Griffon",
            "Strawberry Sunrise",
            "Sassaflash",
            "Royal Riff",
            "Lemon Hearts",           

        };

        this.descriptions = new String[][]{
            {"01", "glitter"}, 
            {"02", "glitter"}, 
            {"03", "glitter"},
            {"04", "glitter"}, 
            {"05", "glitter"}, 
            {"06", "glitter"},
            {"07", "glitter"},
            {"08", "glitter"},
            {"09", "glitter"},
            {"10", "glitter"},
            {"11", "glitter"}, 
            {"12", "glitter"},
            {"13", "glitter"}, 
            {"14", "glitter"}, 
            {"15", "glitter"},
            {"16", "glitter"},
            {"17", "glitter"}, 
            {"18", "glitter"},
            {"19", "glitter"},
            {"20", "glitter"}, 
            {"21", "glitter"},
            {"22", "glitter"}, 
            {"23", "glitter"},
            {"24", "glitter"},};

        this.wave = 10;
        this.waveName = "" + this.wave;
        this.ponies = new Pony[names.length];

        this.patterns.add(new AZPattern());

        this.init();

    }
}
