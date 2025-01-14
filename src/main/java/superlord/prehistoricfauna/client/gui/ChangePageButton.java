package superlord.prehistoricfauna.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ChangePageButton extends Button {
    private final boolean right;
    public int lastpage = 1;
    private final int color;

    public ChangePageButton(int x, int y, boolean right, int color, OnPress press) {
        super(x, y, 23, 10, new TextComponent(""), press);
        this.right = right;
        this.color = color;
    }

    @Override
    public void renderButton(@NotNull PoseStack matrixStack, int mouseX, int mouseY, float partial) {
        if (this.active) {
            boolean flag = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            RenderSystem.setShaderTexture(0, new ResourceLocation("prehistoricfauna:textures/gui/paleopedia/widgets.png"));
            int i = 0;
            int j = 64;
            if (flag) {
                i += 23;
            }

            if (!this.right) {
                j += 13;
            }
            j += color * 23;

            this.blit(matrixStack, this.x, this.y, i, j, width, height);
        }
    }
}
