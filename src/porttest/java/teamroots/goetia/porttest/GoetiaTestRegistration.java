package teamroots.goetia.porttest;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.*;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
@EventBusSubscriber(modid="goetia")
public final class GoetiaTestRegistration {
 private record Entry(String name,Consumer<GameTestHelper> body){}
 private static final List<Entry> TESTS=List.of(
  new Entry("alignment_and_death_keep_released_rules",GoetiaPortTests::alignmentAndDeathKeepReleasedRules),
  new Entry("spell_thresholds_and_flight_quirk",GoetiaPortTests::spellThresholdsAndFlightQuirk),
  new Entry("symbol_needs_extra_xp_and_charges101_ticks",GoetiaPortTests::symbolNeedsExtraXpAndCharges101Ticks),
  new Entry("forge_consumes_whole_stack_and_all_demons",GoetiaPortTests::forgeConsumesWholeStackAndAllDemons),
  new Entry("cleanse_wins_over_blood_summoning",GoetiaPortTests::cleanseWinsOverBloodSummoning),
  new Entry("demons_and_holy_water_keep_their_attributes",GoetiaPortTests::demonsAndHolyWaterKeepTheirAttributes),
  new Entry("voracious_damage_runs_after_cooldown_and_before_armor",GoetiaPortTests::voraciousDamageRunsAfterCooldownAndBeforeArmor),
  new Entry("inner_fire_does_not_change_cooldown_selection",GoetiaPortTests::innerFireDoesNotChangeCooldownSelection),
  new Entry("fireball_keeps_fixed_legacy_acceleration",GoetiaPortTests::fireballKeepsFixedLegacyAcceleration),
  new Entry("released_fireballs_impact_deflecting_mobs",GoetiaPortTests::releasedFireballsImpactDeflectingMobs),
  new Entry("blood_knife_produces_one_typed_bottle",GoetiaPortTests::bloodKnifeProducesOneTypedBottle),
  new Entry("holy_water_uses_real_water_and_breathing_paths",GoetiaPortTests::holyWaterUsesRealWaterAndBreathingPaths),
  new Entry("effect_ticks_before_movement_exactly_once",GoetiaPortTests::effectTicksBeforeMovementExactlyOnce),
  new Entry("alignment_threshold_is_captured_per_player",GoetiaPortTests::alignmentThresholdIsCapturedPerPlayer),
  new Entry("all_recipes_load_and_cross_requires_pristine",GoetiaResourceTests::allRecipesLoadAndCrossRequiresPristine),
  new Entry("dungeon_loot_actually_generates_both_note_families",GoetiaResourceTests::dungeonLootActuallyGeneratesBothNoteFamilies)
 );
 private static Identifier id(String name){return Identifier.fromNamespaceAndPath("goetia",name);}
 @SubscribeEvent public static void functions(RegisterEvent event){if(event.getRegistryKey().equals(Registries.TEST_FUNCTION))event.register(Registries.TEST_FUNCTION,helper->{for(var test:TESTS)helper.register(id(test.name()),test.body());});}
 @SubscribeEvent public static void tests(RegisterGameTestsEvent event){for(var test:TESTS){var environment=event.registerEnvironment(id("isolated_"+test.name()),new TestEnvironmentDefinition.AllOf(List.of()));event.registerTest(id(test.name()),new FunctionGameTestInstance(ResourceKey.create(Registries.TEST_FUNCTION,id(test.name())),new TestData<>(environment,id("port_empty9"),80,0,true)));}}
 private GoetiaTestRegistration(){}
}
