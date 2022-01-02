package de.royzer.perspektive

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.option.Perspective
import net.minecraft.client.util.InputUtil
import org.lwjgl.glfw.GLFW

object Perspektive : ModInitializer {
    // the pitch and yaw of the player when in freecam mode
    @JvmStatic
    var pitch: Float = 0F
    @JvmStatic
    var yaw: Float = 0F
    var freeLookEnabled = false
    private var perspectiveBefore = Perspective.FIRST_PERSON

    override fun onInitialize() {
        val useKeybind = KeyBindingHelper.registerKeyBinding(
            KeyBinding("Freelook", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Y, "Perspektive")
        )
        ClientTickEvents.END_CLIENT_TICK.register {
            if (useKeybind.wasPressed()) {
                if (!freeLookEnabled)
                    perspectiveBefore = MinecraftClient.getInstance().options.perspective
                freeLookEnabled = !freeLookEnabled
                if (freeLookEnabled)
                    MinecraftClient.getInstance().options.perspective = Perspective.THIRD_PERSON_BACK
                else
                    MinecraftClient.getInstance().options.perspective = perspectiveBefore
            }
        }
    }
}