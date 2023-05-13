package net.gerasiov.particleapi.particles;

import java.util.function.Predicate;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.Particle.DustTransition;
import org.bukkit.entity.Entity;

import net.gerasiov.particleapi.events.ParticleSpawnEvent;

public class DustParticle extends RegularParticle{
    private Color color;
    private Color secondaryColor;
    private float size;


    /**
     * Creates a new {@link DustParticle} object with the specified parameters.
     *
     * @param location The location for the particle.
     * @param color The color for the particle.
     * @param size The size for the particle.
     */
    public DustParticle(Location location, Color color, float size) {
        super(Particle.REDSTONE, location);
        this.color = color;
        this.secondaryColor = null;
        this.size = size;
    }

    /**
     * Creates a new {@link DustParticle} object with the specified parameters.
     *
     * @param location The location for the particle.
     * @param color The color for the particle.
     * @param secondaryColor The secondary color for the particle, towards which the main color will transition.
     * @param size The size for the particle.
     */
    public DustParticle(Location location, Color color, Color secondaryColor, float size) {
        super(Particle.DUST_COLOR_TRANSITION, location);
        this.color = color;
        this.secondaryColor = secondaryColor;
        this.size = size;
    }

    public Color getColor() {
        return this.color;
    }

    public Color getSecondaryColor() {
        return this.secondaryColor;
    }

    public float getSize() {
        return this.size;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setSecondaryColor(Color secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public void setSize(float size) {
        this.size = size;
    }

    @Override
    public DustParticle clone() {
        if (getType() == Particle.REDSTONE) {
            return new DustParticle(getLocation(), this.color, this.size);
        }
        return new DustParticle(getLocation(), this.color, this.secondaryColor, this.size);
    }

    @Override
    public void spawn() {
        ParticleSpawnEvent particleSpawnEvent = new ParticleSpawnEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(particleSpawnEvent);

        if (!particleSpawnEvent.isCancelled()) {
            if (getType() == Particle.REDSTONE) {
                DustOptions dustOptions = new DustOptions(color, size);
                getLocation().getWorld().spawnParticle(Particle.REDSTONE, getLocation(), 1, dustOptions);
            } else {
                DustTransition dustTransition = new DustTransition(color, secondaryColor, size);
                getLocation().getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, getLocation(), 1, dustTransition);
            }
        }
    }
}
