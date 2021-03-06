package com.gempukku.lotro.cards.set18.site;

import com.gempukku.lotro.logic.cardtype.AbstractShadowsSite;
import com.gempukku.lotro.common.Keyword;

/**
 * Set: Treachery & Deceit
 * Twilight Cost: 4
 * Type: Site
 * Game Text: River.
 */
public class Card18_138 extends AbstractShadowsSite {
    public Card18_138() {
        super("Sirannon Ruins", 4, Direction.RIGHT);
        addKeyword(Keyword.RIVER);
    }
}
