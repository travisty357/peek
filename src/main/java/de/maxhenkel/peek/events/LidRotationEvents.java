package de.maxhenkel.peek.events;

import de.maxhenkel.peek.interfaces.PeekShulkerBoxBlockEntity;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.state.BlockState;

public class LidRotationEvents {

    public static void register() {
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            if (hand != InteractionHand.MAIN_HAND || player.isSpectator()) {
                return InteractionResult.PASS;
            }
            if (!player.getItemInHand(hand).is(Items.STICK)) {
                return InteractionResult.PASS;
            }
            BlockPos pos = hitResult.getBlockPos();
            BlockState state = level.getBlockState(pos);
            if (!(state.getBlock() instanceof ShulkerBoxBlock)) {
                return InteractionResult.PASS;
            }
            if (!level.isClientSide() && level.getBlockEntity(pos) instanceof PeekShulkerBoxBlockEntity rotatable) {
                int step = player.isShiftKeyDown() ? 7 : 1; // sneak = reverse direction
                rotatable.peek$setRotation(rotatable.peek$getRotation() + step);
                //level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);
            }
            return InteractionResult.SUCCESS;
        });
    }
}