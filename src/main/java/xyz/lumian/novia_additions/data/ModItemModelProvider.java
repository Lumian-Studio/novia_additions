package xyz.lumian.novia_additions.data;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import xyz.lumian.novia_additions.Define;
import xyz.lumian.novia_additions.block.ModBlocks;
import xyz.lumian.novia_additions.item.ModItems;



//**********************************************************************************************************************
public class ModItemModelProvider
    extends ItemModelProvider
{
    //******************************************************************************************************************
    public ModItemModelProvider(final PackOutput output, final ExistingFileHelper helper)
    {
        super(output, Define.MOD_ID, helper);
    }
    
    //==================================================================================================================
    @Override
    protected void registerModels()
    {
        // BLOCKS
        this.simpleBlockItem(ModBlocks.DEMITHRIL_STATUE.get())
            .transforms()
                //======
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
                    .rotation(75f, -180f, 0f)
                    .scale(0.375f)
                .end()
                //======
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND)
                    .rotation(75f, -180f, 0f)
                    .scale(0.375f)
                .end()
                //======
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
                    .scale(0.4f)
                .end()
                //======
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
                    .scale(0.4f)
                .end()
                //======
                .transform(ItemDisplayContext.GROUND)
                    .scale(0.25f)
                .end()
                //======
                .transform(ItemDisplayContext.FIXED)
                    .scale(0.4f)
                    .translation(-0.25f, -3.5f, -0.25f)
                    .rotation(0f, 180f, 0f)
                .end()
                //======
                .transform(ItemDisplayContext.GUI)
                    .rotation(26f, -32f, 0f)
                    .translation(0f, -2.25f, 0f)
                    .scale(0.4f)
                .end()
            .end();
        
        this.simpleBlockItem(ModBlocks.DEEPSLATE_NOVIUM_ORE.get());
        this.simpleBlockItem(ModBlocks.NOVIUM_BLOCK.get());
        this.simpleBlockItem(ModBlocks.NOVIUM_PELLET_BLOCK.get());
        this.simpleBlockItem(ModBlocks.END_STONE_DEMURIUM_ORE.get());
        this.simpleBlockItem(ModBlocks.DEMURIUM_BLOCK.get());
        this.simpleBlockItem(ModBlocks.DEMITHRIUM_BLOCK.get());
        this.simpleBlockItem(ModBlocks.GHOST_BLOCK.get());
        
        // ITEMS
        this.withExistingParent(ModItems.MUSIC_DISC_EVOLUTION.getId().toString(), "item/template_music_disc")
            .texture("layer0", ModItems.MUSIC_DISC_EVOLUTION.getId().withPrefix("item/"));
        this.withExistingParent(ModItems.MUSIC_DISC_REVOLUTION.getId().toString(), "item/template_music_disc")
            .texture("layer0", ModItems.MUSIC_DISC_REVOLUTION.getId().withPrefix("item/"));
        
        this.basicItem(ModItems.NOVIA_BANNER_PATTERN.get());
        this.basicItem(ModItems.NOVIUM_PELLET.get());
        this.basicItem(ModItems.NOVIUM_INGOT.get());
        this.basicItem(ModItems.DEMURIUM_CRYSTAL.get());
        this.basicItem(ModItems.DEMITHRIUM_INGOT.get());
        this.basicItem(ModItems.NOVIUM_NUGGET.get());
        this.basicItem(ModItems.DEMITHRIUM_NUGGET.get());
        this.basicItem(ModItems.NOVIUM_UPGRADE_SMITHING_TEMPLATE.get());
        this.basicItem(ModItems.DEMITHRIUM_UPGRADE_SMITHING_TEMPLATE.get());
        
        this.handheldItem(ModItems.TRUTHSEEKER.get());
        this.handheldItem(ModItems.NOVIUM_PICKAXE.get());
        this.handheldItem(ModItems.NOVIUM_AXE.get());
        this.handheldItem(ModItems.NOVIUM_HOE.get());
        this.handheldItem(ModItems.NOVIUM_SWORD.get());
        this.handheldItem(ModItems.NOVIUM_SHOVEL.get());
        this.handheldItem(ModItems.DEMITHRIUM_PICKAXE.get());
        this.handheldItem(ModItems.DEMITHRIUM_AXE.get());
        this.handheldItem(ModItems.DEMITHRIUM_HOE.get());
        this.handheldItem(ModItems.DEMITHRIUM_SWORD.get());
        this.handheldItem(ModItems.DEMITHRIUM_SHOVEL.get());
        
        ModItems.ITEMS.getEntries().forEach(item -> {
            if (item.get() instanceof ArmorItem aitem)
            {
                this.basicItem(aitem);
            }
        });
    }
}
