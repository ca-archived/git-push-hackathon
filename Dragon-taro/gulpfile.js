const gulp = require("gulp");
const sass = require("gulp-sass");

const paths = {
  scss: "src/css/",
  css: "static/css/"
};
const scssPath = paths.scss + "**/*.scss";

gulp.task("build", function() {
  return gulp
    .src(scssPath)
    .pipe(sass())
    .pipe(gulp.dest(paths.css));
});

gulp.task("watch", function() {
  gulp.watch(scssPath, ["build"]);
});
