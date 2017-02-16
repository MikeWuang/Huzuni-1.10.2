package net.halalaboos.huzuni.mc;

import net.minecraft.util.text.ITextComponent;

/**
 * When you add a player as a friend with an alias, any occurrence of their original name is replaced with their alias.
 *
 * <p>The issue with this is that Java's String#replaceAll method is expensive, and if we were to call it every
 * tick when a chat line is rendered, it could potentially have a negative performance impact.</p>
 *
 * <p>You could circumvent this by having this be done whenever a ChatLine is added, but then you encounter an issue
 * where when you remove a player as a friend, their alias still remains in chat, or if you add a player as a friend,
 * any previous messages aren't replaced.</p>
 *
 * <p>Our solution is to modify ChatLine's class and include a modified String that will represent the message with
 * the names replaced.  When you add a player as a friend, this String will be set, and when you remove them, it will
 * also be set.</p>
 *
 * TODO: Repackage this
 *
 * @author brudin
 */
public interface ModifiableTextComponent {

	ITextComponent getModifiedText();

	void setModifiedText(ITextComponent modifiedText);

}
