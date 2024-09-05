public class Line {
    private Point start;
    private Point end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    // Метод для створення перпендикулярного відрізка
    public Line perpendicular(double length) {
        double dx = end.x - start.x;
        double dy = end.y - start.y;
        double magnitude = Math.sqrt(dx * dx + dy * dy);
        double perpDx = -dy / magnitude;
        double perpDy = dx / magnitude;

        Point p1 = new Point(start.x + perpDx * length / 2, start.y + perpDy * length / 2);
        Point p2 = new Point(start.x - perpDx * length / 2, start.y - perpDy * length / 2);

        return new Line(p1, p2);
    }
}
