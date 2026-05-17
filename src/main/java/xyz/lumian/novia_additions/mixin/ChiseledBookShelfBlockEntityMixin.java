package xyz.lumian.novia_additions.mixin;

import net.minecraft.core.NonNullList;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import xyz.lumian.novia_additions.impl.ChiseledBookShelfBlockEntityExtension;



//**********************************************************************************************************************
@Mixin(ChiseledBookShelfBlockEntity.class)
public abstract class ChiseledBookShelfBlockEntityMixin
    implements ChiseledBookShelfBlockEntityExtension
{
    //******************************************************************************************************************
    @Shadow @Final private NonNullList<ItemStack> items;
    
    //******************************************************************************************************************
    @Unique
    @Override
    public void noviaAdditions$setItemNoUpdate(final int slot, final ItemStack stack)
    {
        if (stack.is(ItemTags.BOOKSHELF_BOOKS))
        {
            this.items.set(slot, stack);
        }
    }
}
