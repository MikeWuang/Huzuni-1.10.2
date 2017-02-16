package net.halalaboos.huzuni.mc.mixin;

import net.halalaboos.huzuni.mc.ModifiableTextComponent;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.client.gui.ChatLine.class)
public abstract class MixinChatLine implements ModifiableTextComponent {

	@Shadow
	public abstract ITextComponent getChatComponent();

	private ITextComponent modifiedText;

	@Override
	public ITextComponent getModifiedText() {
		return modifiedText == null ? getChatComponent() : modifiedText;
	}

	@Override
	public void setModifiedText(ITextComponent modifiedText) {
		this.modifiedText = modifiedText;
	}
}
