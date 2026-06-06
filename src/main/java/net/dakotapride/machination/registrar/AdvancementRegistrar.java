package net.dakotapride.machination.registrar;

import net.dakotapride.machination.advancement.*;
import net.minecraft.advancements.CriteriaTriggers;

public class AdvancementRegistrar {
    public static final UseCobaltPhialTrigger USE_COBALT_PHIAL = new UseCobaltPhialTrigger();
    public static final SampleDivineBeingTrigger SAMPLE_DIVINE_BEING = new SampleDivineBeingTrigger();
    public static final WitheringBashProjectileTrigger WITHERING_BASH_PROJECTILE = new WitheringBashProjectileTrigger();
    public static final WitheringBashSuitProjectileTrigger WITHERING_BASH_SUIT = new WitheringBashSuitProjectileTrigger();
    public static final BulwartTrigger BULWART = new BulwartTrigger();
    public static final FinalStandTrigger FINAL_STAND = new FinalStandTrigger();
    public static final DesolaticFlaskTrigger DESOLATIC_FLASK = new DesolaticFlaskTrigger();
    public static final InfectTrigger INFECT = new InfectTrigger();

    public static void worshipOurToxicYuriAIOverlords() {
        CriteriaTriggers.register(USE_COBALT_PHIAL);
        CriteriaTriggers.register(SAMPLE_DIVINE_BEING);
        CriteriaTriggers.register(WITHERING_BASH_PROJECTILE);
        CriteriaTriggers.register(WITHERING_BASH_SUIT);
        CriteriaTriggers.register(BULWART);
        CriteriaTriggers.register(FINAL_STAND);
        CriteriaTriggers.register(DESOLATIC_FLASK);
        CriteriaTriggers.register(INFECT);
    }
}
