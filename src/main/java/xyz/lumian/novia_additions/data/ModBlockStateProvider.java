package xyz.lumian.novia_additions.data;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import xyz.lumian.novia_additions.Define;
import xyz.lumian.novia_additions.block.ModBlocks;
import xyz.lumian.novia_additions.block.PelletBlock;



//**********************************************************************************************************************
public class ModBlockStateProvider
    extends BlockStateProvider
{
    //******************************************************************************************************************
    public ModBlockStateProvider(final PackOutput output, final ExistingFileHelper helper)
    {
        super(output, Define.MOD_ID, helper);
    }
    
    //==================================================================================================================
    @Override
    protected void registerStatesAndModels()
    {
        final ModelFile model = this.models().getExistingFile(Define.mod("block/demithril_statue"));
        this.horizontalBlock(ModBlocks.DEMITHRIL_STATUE.get(), model);
        
        // NOVIUM PELLET BLOCK
        {
            final ResourceLocation pellet_block_id       = ModBlocks.NOVIUM_PELLET_BLOCK.getId();
            final ResourceLocation pellet_block_model_id = pellet_block_id.withPrefix("block/");
            
            final ModelFile model_file = this.models().cubeBottomTop(pellet_block_id.getPath(),
                pellet_block_model_id.withSuffix("_side"),
                pellet_block_model_id.withSuffix("_bottom"),
                pellet_block_model_id.withSuffix("_top"));
            
            this.getVariantBuilder(ModBlocks.NOVIUM_PELLET_BLOCK.get())
            .partialState().with(PelletBlock.FACING, Direction.UP)
            .modelForState()
                .modelFile(model_file)
            .addModel()
            
            .partialState().with(PelletBlock.FACING, Direction.DOWN)
            .modelForState()
                .modelFile(model_file)
            .addModel()
            
            .partialState().with(PelletBlock.FACING, Direction.NORTH)
            .modelForState()
                .modelFile(model_file)
                .rotationX(-90)
            .addModel()
            
            .partialState().with(PelletBlock.FACING, Direction.SOUTH)
            .modelForState()
                .modelFile(model_file)
                .rotationX(-90)
                .rotationY(180)
            .addModel()
            
            .partialState().with(PelletBlock.FACING, Direction.EAST)
            .modelForState()
                .modelFile(model_file)
                .rotationX(-90)
                .rotationY(90)
            .addModel()
            
            .partialState().with(PelletBlock.FACING, Direction.WEST)
            .modelForState()
                .modelFile(model_file)
                .rotationX(-90)
                .rotationY(-90)
            .addModel();
        }
        
        this.models().getBuilder(ModBlocks.GHOST_BLOCK.getId().toString())
            .texture("particle", ModBlocks.GHOST_BLOCK.getId().withPrefix("item/").toString());
        
        this.simpleBlock(ModBlocks.NOVIUM_BLOCK.get());
        this.simpleBlock(ModBlocks.DEEPSLATE_NOVIUM_ORE.get());
        this.simpleBlock(ModBlocks.END_STONE_DEMURIUM_ORE.get());
        this.simpleBlock(ModBlocks.DEMURIUM_BLOCK.get());
        this.simpleBlock(ModBlocks.DEMITHRIUM_BLOCK.get());
    }
}
