package net.gerasiov.particleapi.particles;

import net.gerasiov.particleapi.events.ParticleSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;

import java.util.Collection;
import java.util.function.Predicate;

public class SpellMobParticle implements ParticlePoint{
    private final Particle type;
    private Location location;
    private double red;
    private double green;
    private double blue;
    // I don't know what this value is, maybe alpha? Until then, it will be called extra. extra=1D should produce normal results.
    private double extra;


    /**
     * Creates a new {@link SpellMobParticle} object with the specified parameters.
     *
     * @param type Particle type. Can be either {@link Particle#SPELL_MOB} or {@link Particle#SPELL_MOB_AMBIENT}.
     * @param location The location for the particle.
     * @param color The color for the particle.
     * @param extra The extra value for the particle. Presumably alpha, 1D should produce normal results.
     */
    public SpellMobParticle(Particle type, Location location, Color color, double extra) {
        if (!(type == Particle.SPELL_MOB || type == Particle.SPELL_MOB_AMBIENT)) {
            throw new IllegalArgumentException("Invalid particle type provided");
        }

        this.type = type;
        this.location = location;
        this.red = color.getRed() / 255D;
        this.green = color.getGreen() / 255D;
        this.blue = color.getBlue() / 255D;
        this.extra = extra;
    }

    /**
     * Creates a new {@link SpellMobParticle} object with the specified parameters.
     *
     * @param type Particle type. Can be either {@link Particle#SPELL_MOB} or {@link Particle#SPELL_MOB_AMBIENT}.
     * @param location The location for the particle.
     * @param red The red component of the color.;
     * @param green The green component of the color.;
     * @param blue The blue component of the color.;
     * @param extra The extra value for the particle. Presumably alpha, 1D should produce normal results.
     */
    public SpellMobParticle(Particle type, Location location, int red, int green, int blue, double extra) {
        if (!(type == Particle.SPELL_MOB || type == Particle.SPELL_MOB_AMBIENT)) {
            throw new IllegalArgumentException("Invalid particle type provided");
        }

        this.type = type;
        this.location = location;
        this.red = red / 255D;
        this.green = green / 255D;
        this.blue = blue / 255D;
        this.extra = extra;
    }

    /**
     * Creates a new {@link SpellMobParticle} object with the specified parameters.
     *
     * @param type Particle type. Can be either {@link Particle#SPELL_MOB} or {@link Particle#SPELL_MOB_AMBIENT}.
     * @param location The location for the particle.
     * @param red The red component of the color. The value must be a double between 0 and 1, also divisible by 1/255.;
     * @param green The green component of the color. The value must be a double between 0 and 1, also divisible by 1/255.;
     * @param blue The blue component of the colo. The value must be a double between 0 and 1, also divisible by 1/255.;
     * @param extra The extra value for the particle. Presumably alpha, 1D should produce normal results.
     */
    public SpellMobParticle(Particle type, Location location, double red, double green, double blue, double extra) {
        if (!(type == Particle.SPELL_MOB || type == Particle.SPELL_MOB_AMBIENT)) {
            throw new IllegalArgumentException("Invalid particle type provided");
        }

        this.type = type;
        this.location = location;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.extra = extra;
    }

    @Override
    public Particle getType() {
        return this.type;
    }

    @Override
    public Location getLocation() {
        return null;
    }

    public int getRed() {
        return (int) (this.red * 255);
    }

    public int getGreen() {
        return (int) (this.green * 255);
    }

    public int getBlue() {
        return (int) (this.blue * 255);
    }

    public double getExtra() {
        return this.extra;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    public void setRed(double red) {
        this.red = red;
    }

    public void setGreen(double green) {
        this.green = green;
    }

    public void setBlue(double blue) {
        this.blue = blue;
    }

    public void setExtra(double extra) {
        this.extra = extra;
    }

    public void setColor(Color color) {
        this.red = color.getRed() / 255D;
        this.green = color.getGreen() / 255D;
        this.blue = color.getBlue() / 255D;
    }

    @Override
    public SpellMobParticle clone() {
        return new SpellMobParticle(this.type, this.location, this.red, this.green, this.blue, this.extra);
    }

    @Override
    public Collection<Entity> getNearbyEntities(double radiusX, double radiusY, double radiusZ) {
        return this.location.getWorld().getNearbyEntities(this.location, radiusX, radiusY, radiusZ);
    }

    @Override
    public Collection<Entity> getNearbyEntities(double radiusX, double radiusY, double radiusZ, Predicate<Entity> filter) {
        return this.location.getWorld().getNearbyEntities(this.location, radiusX, radiusY, radiusZ, filter);
    }

    @Override
    public void spawn() {
        ParticleSpawnEvent particleSpawnEvent = new ParticleSpawnEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(particleSpawnEvent);

        if (!particleSpawnEvent.isCancelled()) {
            location.getWorld().spawnParticle(type, location, 0, red, green, blue, extra);
        }
    }
}
