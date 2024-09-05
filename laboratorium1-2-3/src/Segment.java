public class Segment {
    private Point start;
    private Point end;

    public Segment(Point end, Point start) {
        this.end = end;
        this.start = start;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }


    public double length(){
        double dx = end.x - start.x;
        double dy = end.y - start.y;
        return Math.sqrt(dx*dx+dy*dy);
    }

    public String toSvg(){
        String line = "<svg><line x1=\"" + start.x + "\" y1=\"" + start.y + "\" x2=\"" + end.x + "\" x1=\"" + end.y + " stroke=\"black\" />   </svg>";
        return line;
    }

    public static Segment[] perpendicuarSegments(Segment segment, Point point){
        double dx = segment.end.x - segment.start.x;
        double dy = segment.end.y - segment.start.y;
        double length = segment.length();

        double normalizedDx = dx / length;
        double normalizedDy = dy / length;

        double perpDx1 = -normalizedDy * length;
        double perpDy1 = normalizedDx * length;
        double perpDx2 = normalizedDy * length;
        double perpDy2 = -normalizedDx * length;

        Point end1 = new Point(point.x + perpDx1, point.y + perpDy1);
        Point end2 = new Point(point.x + perpDx2, point.y + perpDy2);

        Segment perpSegment1 = new Segment(point, end1);
        Segment perpSegment2 = new Segment(point, end2);

        return new Segment[] { perpSegment1, perpSegment2 };
    }
}
