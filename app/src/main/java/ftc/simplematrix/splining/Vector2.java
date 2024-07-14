package ftc.simplematrix.splining;

import org.checkerframework.checker.nullness.qual.NonNull;

public class Vector2 {
    public double x;
    public double y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Add two vectors.
     * @param v1 The first vector
     * @param v2 The second vector
     * @return A vector, with the elements of both added to each other
     */
    @NonNull
    // @Contract(value = "_, _ -> new", pure = true)
    public static Vector2 plus(@NonNull Vector2 v1, @NonNull Vector2 v2) {
        return new Vector2(v1.x + v2.x, v1.y + v2.y);
    }

    /**
     * Add two vectors.
     * @param other The vector to add
     * @return A new vector, with the elements of both added to each other
     */
    public Vector2 plus(@NonNull Vector2 other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    /**
     * Add two vectors.
     * @param v1 The first vector
     * @param v2 The second vector
     * @return A vector, with the elements of both added to each other
     */
    @NonNull
    // @Contract(value = "_, _ -> new", pure = true)
    public static Vector2 minus(@NonNull Vector2 v1, @NonNull Vector2 v2) {
        return new Vector2(v1.x - v2.x, v1.y - v2.y);
    }

    /**
     * Add two vectors.
     * @param other The vector to add
     * @return A new vector, with the elements of both added to each other
     */
    public Vector2 minus(@NonNull Vector2 other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }

    /**
     * Multiply a vector by a scalar.
     * @param v1 The vector to scale
     * @param scalar The scalar to multiply by
     * @return The scaled vector
     */
    @NonNull
    // @Contract(value = "_, _ -> new", pure = true)
    public static Vector2 mult(@NonNull Vector2 v1, double scalar) {
        return new Vector2(v1.x * scalar, v1.y * scalar);
    }

    /**
     * Multiply a vector by a scalar.
     * @param scalar The scalar to multiply by
     * @param v1 The vector to scale
     * @return The scaled vector
     */
    @NonNull
    // @Contract(value = "_, _ -> new", pure = true)
    public static Vector2 mult(double scalar, @NonNull Vector2 v1) {
        return new Vector2(v1.x * scalar, v1.y * scalar);
    }

    /**
     * Multiply the vector by a scalar.
     * @param scalar The scalar to multiply by
     * @return The scaled vector
     */
    public Vector2 mult(@NonNull double scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }


    /**
     * Find the dot product of two vectors.
     * @param v1 The first vector
     * @param v2 The second vector
     * @return The dot product of the vectors
     */
    @NonNull
    // @Contract(value = "_, _ -> new", pure = true)
    public static double dot(@NonNull Vector2 v1, @NonNull Vector2 v2) {
        return v1.x * v2.x + v1.y * v2.y;
    }

    /**
     * Add two vectors.
     * @param other The vector to do dot product with
     * @return The dot product of this vector with the other.
     */
    public double dot(@NonNull Vector2 other) {
        return this.x * other.x + this.y * other.y;
    }

}
