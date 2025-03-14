## `ST_X` [esql-st_x]

**Syntax**

:::{image} ../../../../../images/st_x.svg
:alt: Embedded
:class: text-center
:::

**Parameters**

`point`
:   Expression of type `geo_point` or `cartesian_point`. If `null`, the function returns `null`.

**Description**

Extracts the `x` coordinate from the supplied point. If the points is of type `geo_point` this is equivalent to extracting the `longitude` value.

**Supported types**

| point | result |
| --- | --- |
| cartesian_point | double |
| geo_point | double |

**Example**

```esql
ROW point = TO_GEOPOINT("POINT(42.97109629958868 14.7552534006536)")
| EVAL x =  ST_X(point), y = ST_Y(point)
```

| point:geo_point | x:double | y:double |
| --- | --- | --- |
| POINT(42.97109629958868 14.7552534006536) | 42.97109629958868 | 14.7552534006536 |


