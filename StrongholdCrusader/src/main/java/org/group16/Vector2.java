package org.group16;

public record Vector2(double x, double y) {


    public static double dot(Vector2 a, Vector2 b) {
        return a.x * b.x + a.y * b.y;
    }

    public static Vector2 mul(Vector2 a, double b) {
        return new Vector2(a.x * b, a.y * b);
    }

    public static Vector2 div(Vector2 a, double b) {
        return new Vector2(a.x / b, a.y / b);
    }

    public static Vector2 add(Vector2 a, Vector2 b) {
        return new Vector2(a.x + b.x, a.y + b.y);
    }

    public static Vector2 sub(Vector2 a, Vector2 b) {
        return new Vector2(a.x - b.x, a.y - b.y);
    }

    public Vector2 normal() {
        return new Vector2(y, -x);
    }

    public Vector2 normalize() {
        return div(this, length());
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector2 negate() {
        return new Vector2(-x, -y);
    }
}
