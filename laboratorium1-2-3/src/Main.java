public class Main {
    public static void main(String[] args) {
        Point point1 = new Point(1, 2);
        Point point2 = new Point(3, 4);
        Segment segment = new Segment(point1, point2);

        System.out.println("Segment length: " + segment.length());
        System.out.println(segment.toSvg());

        Point startPoint = new Point(0, 0);

        Segment[] perpendicularSegments = Segment.perpendicuarSegments(segment, startPoint);
        System.out.println("Перпендикулярні відрізки:");
        for (Segment perpSegment : perpendicularSegments) {
            System.out.println("Початок: (" + perpSegment.getStart().x + ", " + perpSegment.getStart().y + ")");
            System.out.println("Кінець: (" + perpSegment.getEnd().x + ", " + perpSegment.getEnd().y + ")");
            System.out.println("SVG код: " + perpSegment.toSvg());
        }

 Point p1 = new Point(1,2);
 Point p2 = new Point(3,4);
 Point p3 = new Point(5,6);
 Point p4 = new Point(7,8);

        Point[] points = {p1,p2, p3, p4};

        Style style = new Style(null, null, null);
        Polygon polygon1 = new Polygon(points, null);
        Polygon polygon2 = new Polygon(points, style);

        System.out.println(polygon1.toSvg());

        SvgScene scene = new SvgScene();
        scene.addPolygon(polygon1);
        scene.addPolygon(polygon2);

        scene.saveHtml("C:\\Users\\User\\Desktop\\scene.txt");
    }

}
