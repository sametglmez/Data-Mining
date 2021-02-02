import matplotlib.pyplot as plt
import math
import numpy as np
from random import seed
from random import randint
from matplotlib.pyplot import *
from numpy import linspace


class Point:
    def __init__(self, xPoint, yPoint, index):
        self.xPoint = xPoint
        self.yPoint = yPoint
        self.visited = 0
        self.status = 0  # 0 boş demek 1 noise 2 clustera üye
        self.index = index

    def Display(self):
        print("x Point : ", self.xPoint, "  Y Point : ", self.yPoint, " Visited : ", self.visited)


def automatic(xPoints, yPoints):
    proximity = []
    resultProximity = []

    for x in range(len(xPoints)):
        distances = []
        result = []
        for y in range(len(yPoints)):

            if x == y:
                continue
            distance = math.sqrt(((xPoints[x] - xPoints[y]) ** 2) + ((yPoints[x] - yPoints[y]) ** 2))
            distances.append(distance)

        result = findClosestPoints(15, distances)
        proximity.append(result)

    for temp in range(len(proximity)):
        resultProximity.append(Average(proximity[temp]))

    return Average(resultProximity)

def Average(lst):
    return sum(lst) / len(lst)

def findClosestPoints(num, proximity):
    result = []

    proximity.sort()
    #print(proximity)
    for i in range(num):
        result.append(proximity[i])

    return result


def DBScan(xPoints, yPoints,radius):
    Points = []

    for counter in range(len(xPoints)):
        # valueX = randint(0, 1000)
        # valueY = randint(0, 1000)
        # print(valueX, "   ", valueY)
        Points.append(Point(xPoints[counter], yPoints[counter], counter))

    #for counter in range(len(Points)):
    #    Points[counter].Display()
    #    plt.scatter(Points[counter].xPoint, Points[counter].yPoint,s = 100,alpha=0.5,cmap='viridis')

    counter = 0
    # First parameter for minPoints , Second parameter is radius
    generalCluster = DBScanOperation(Points, 4, radius)
    for counter in range(len(generalCluster)):
        x = []
        y = []
        for counterTemp in range(len(generalCluster[counter])):
            #print(counter)
            x.append(generalCluster[counter][counterTemp].xPoint)
            y.append(generalCluster[counter][counterTemp].yPoint)

        plt.scatter(x, y)

    counter = 0
    for counter in range(len(Points)):
        if Points[counter].status == 1:
            plt.scatter(Points[counter].xPoint, Points[counter].yPoint, c='red', s=100, alpha=0.5, cmap='viridis')

    plt.show()


def DBScanOperation(Points, minPoints, radius):
    visitCounter = 0
    i = 0
    generalCluster = []

    while visitCounter != len(Points):

        if Points[i].visited == 1:
            i = i + 1
            visitCounter = visitCounter + 1
            continue
        else:
            Points[i].visited = 1
            k = findMinPoints(Points, Points[i], radius, i, minPoints)

            if k != 0:
                #for l in range(len(k)):
                #   k[l].Display()
                cluster = []
                cluster.append(Points[i])
                temp = 0
                while temp != len(k):
                    #print("Temp : ", temp, " Length of K : ", len(k))
                    if Points[k[temp].index].visited == 0:
                        Points[k[temp].index].visited = 1
                        kMinPoint = findMinPoints(Points, k[temp], radius, temp, minPoints)
                        if kMinPoint != 0:
                            for count in range(len(kMinPoint)):
                                k.append(Points[kMinPoint[count].index])

                    if Points[k[temp].index].status != 2:
                        Points[k[temp].index].status = 2
                        cluster.append(Points[k[temp].index])

                    temp = temp + 1

                generalCluster.append(cluster)
                # generalCluster.append(k)
            else:
                Points[i].status = 1

            i = i + 1
            visitCounter = visitCounter + 1

    return generalCluster


def findMinPoints(Points, clusterPoint, radius, indexOfPoint, minPoint):
    visitCounter = 0
    nearCount = 0
    Result = []
    i = 0
    while visitCounter != len(Points):
        "& indexOfPoint == i (Daha sonradan eklenebilir, yuakrıdan gelen pointin kendini bulmaması için"
        if Points[i].index == clusterPoint.index:
            i = i + 1
            visitCounter = visitCounter + 1
            continue
        else:
            x = clusterPoint.xPoint - Points[i].xPoint
            y = clusterPoint.yPoint - Points[i].yPoint
            if -radius <= x <= radius and -radius <= y <= radius:
                Result.append(Point(Points[i].xPoint, Points[i].yPoint, Points[i].index))
            i = i + 1
            visitCounter = visitCounter + 1

    """y = 0
    print("-------> ",clusterPoint.xPoint,"    ",clusterPoint.yPoint)
    while y != len(Result):
        print(Result[y].xPoint,"   ", Result[y].yPoint)
        y = y + 1"""

    if len(Result) >= minPoint:
        return Result
    else:
        return 0


def main():
    xPoints = []
    yPoints = []
    flag = 0
    print("Hello World!")
    fp = open("C:\\Users\\samet\\Desktop\\spiral.txt", "r")
    for line in fp:
        for word in line.split():
            if flag == 0:
                xPoints.append(float(word))
                flag = 1
            elif flag == 1:
                yPoints.append(float(word))
                flag = 0

    fp.close()
    #x = linspace(-10, 10, 200)
    #y = x ** 2

    """x = np.linspace(-7, 7, 200)
    y = np.linspace(-150, 150, 200)
    x, y = np.meshgrid(x, y)
    print(x)"""
    #arr = [100, 20, 67, 14, 88, 9, 56, 1, 31, 2, 199, 245, 43, 88]
    #DBScan(x,y)
    #findClosestPoints(5, arr)


    """for counter in range(200):
        valueX = randint(150, 175)
        valueY = randint(160, 210)
        print(valueX)

        xPoints.append(valueX)
        yPoints.append(valueY)"""
    #DBScan(xPoints,yPoints)

    radius = automatic(xPoints,yPoints)
    print(radius)
    DBScan(xPoints, yPoints,radius)


if __name__ == '__main__':
    main()
