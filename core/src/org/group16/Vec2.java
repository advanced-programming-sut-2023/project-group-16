package org.group16;

public record Vec2(double x, double y) {


    public static double dot(Vec2 a, Vec2 b) {
        return a.x * b.x + a.y * b.y;
    }

    public static Vec2 mul(Vec2 a, double b) {
        return new Vec2(a.x * b, a.y * b);
    }

    public static Vec2 div(Vec2 a, double b) {
        return new Vec2(a.x / b, a.y / b);
    }

    public static Vec2 add(Vec2 a, Vec2 b) {
        return new Vec2(a.x + b.x, a.y + b.y);
    }

    public static Vec2 sub(Vec2 a, Vec2 b) {
        return new Vec2(a.x - b.x, a.y - b.y);
    }

    public Vec2 normal() {
        return new Vec2(y, -x);
    }

    public Vec2 normalize() {
        return div(this, length());
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public Vec2 negate() {
        return new Vec2(-x, -y);
    }
}
