package com.artemkaxboy.leetcode.p00

import com.artemkaxboy.leetcode.LeetUtils
import java.util.*
import kotlin.system.measureTimeMillis

/**
 * Runtime      241ms   Beats 66.43%
 * Memory       38.3MB  Beats 57.14%
 */
class Leet45 {

    class Solution {

        private val map = TreeMap<Int, Int>()

        fun jump(nums: IntArray): Int {
            val target = nums.size - 1
            if (target == 0) return 0
            map[0] = 0
            map[1] = 1

            var index = 0
            var lastExplored = 0
            while (index < target) {
                val jumpsCount = map[map.ceilingKey(index)]!! + 1
                val longestJumpTo = index + nums[index]
                if (longestJumpTo > lastExplored) {
                    if (longestJumpTo >= target) return jumpsCount
                    lastExplored = longestJumpTo
                    map[lastExplored] = jumpsCount
                }
                index++
            }

            return -1
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val testCase1 = "[2,3,0,1,4]" to 2
            doWork(testCase1)

            val testCase2 = "[2,1]" to 1
            doWork(testCase2)

            val testCase4 = "[0]" to 0
            doWork(testCase4)

            val testCase5 = "[1,2,3,4,5]" to 3
            doWork(testCase5)

            val testCase3 =
                    "[552,258,757,320,475,852,185,99,121,694,384,526,71,214,964,457,12,203,700,648,660,21,961,799,768,198,643,475,866,463,519,459,221,196,192,158,391,473,809,799,626,140,208,459,364,452,915,253,638,38,689,371,701,165,644,807,339,585,907,215,434,401,849,405,850,686,286,55,637,416,760,620,902,970,747,711,541,13,31,504,526,199,757,929,839,289,880,993,676,639,491,571,503,473,367,735,588,784,97,733,579,260,86,58,512,109,118,537,569,3,827,747,376,646,748,971,120,17,612,510,102,816,519,943,533,493,576,70,795,431,255,422,332,381,399,279,715,248,217,719,818,244,892,416,750,176,373,486,327,529,28,694,357,929,669,407,277,355,981,310,831,560,372,931,209,607,627,687,948,876,782,22,623,624,359,925,75,881,29,125,472,262,541,478,631,168,335,662,824,90,341,838,345,513,963,156,177,218,216,29,537,990,630,497,705,800,848,397,946,20,513,147,123,379,99,70,212,506,366,771,317,624,979,289,679,935,834,157,635,721,19,327,607,119,487,194,351,235,828,232,282,412,501,941,145,706,326,940,880,609,724,307,395,334,804,530,876,864,512,175,51,797,842,588,579,95,334,285,34,898,639,193,961,602,412,752,465,808,66,491,586,801,965,245,950,23,726,378,406,904,976,825,486,448,480,183,533,254,468,145,131,738,730,64,382,24,963,860,42,212,932,609,147,52,848,674,742,161,927,589,224,871,684,383,157,378,293,13,568,184,856,53,363,783,581,777,751,266,369,557,756,226,97,582,613,954,519,735,839,137,978,588,813,980,354,464,741,993,814,534,154,295,204,601,589,624,628,536,313,110,229,557,925,631,734,76,560,205,591,796,215,543,237,753,470,460,834,121,614,399,495,398,212,781,22,360,278,956,38,90,877,215,54,604,811,499,614,374,529,454,814,857,266,470,615,471,582,848,323,765,126,774,456,728,9,277,636,541,828,580,366,381,392,914,724,830,977,233,464,546,526,499,298,937,400,374,176,822,401,335,674,132,847,874,770,902,774,376,127,107,731,494,184,577,68,351,765,922,529,97,588,629,116,849,381,365,158,920,654,720,160,685,107,547,963,46,183,868,392,317,607,618,234,838,53,296,685,60,782,42,432,27,987,594,481,957,48,132,666,940,959,782,574,130,31,360,983,652,940,349,332,331,609,337,660,177,912,399,656,63,119,361,900,297,915,22,431,96,506,336,860,72,261,703,959,760,89,194,247,149,700,161,974,658,781,280,632,505,620,2,336,591,40,859,408,898,809,494,776,351,916,198,10,354,189,734,971,893,715,125,556,286,957,667,749,544,838,276,132,497,790,846,845,592,925,892,958,17,482,971,306,990,824,477,874,363,736,465,386,49,569,314,299,572,216,306,615,688,858,710,857,755,285,411,636,150,40,873,78,41,327,791,692,596,747,470,140,471,208,537,667,661,513,606,60,459,953,294,987,981,774,233,157,633,899,833,739,633,53,701,956,351,183,946,253,276,206,367,145,444,207,939,414,849,56,100,715,117,189,296,75,372,751,682,740,17,755,333,956,100,753,181,86,712,998,443,514,211,98,273,677,32,631,254,802,625,428,931,326,994,827,487,212,657,961,930,238,385,765,580,186,829,408,91,350,178,520,202,716,279,319,405,921,550,239,658,548,720,683,256,473,176,235,411,905,25,907,820,90,897,137,118,5,41,487,672,929,890,691,377,128,27,311,373,281,277,857,304,518,390,403,336,789,34,885,75,505,988,886,967,315,511,860,793,406,82,456,45,107,410,120,477,289,670,487,844,120,546,467,227,713,987,483,277,135,530,274,711,184,756,584,127,367,382,298,252,709,612,386,841,913,466,195,998,502,154,553,225,269,612,678,89,501,89,325,801,868,627,820,58,128,948,461,755,349,699,923,328,428,180,614,689,215,252,433,90,559,620,450,760,559,992,338,800,807,941,372,339,281,154,104,58,668,717,38,620,791,320,847,323,900,291,596,292,295,998,483,32,962,254,11,750,821,979,102,998,10,335,154,460,334,70,812,864,519,967,410,435,161,384,188,420,425,561,870,749,827,188,196,700,630,775,965,588,508,28,559,964,138,713,678,79,633,561,86,558,317,396,843,722,967,793,751,570,590,268,214,216,59,790,677,789,919,137,629,262,499,839,846,218,803,667,141,505,453,348,721,308,232,213,398,62,565,638,652,718,75,214,798,944,897,986,791,816,230,78,224,865,243,36,965,61,984,477,350,460,132,834,611,800,503,789,960,359,547,695,647,441,992,767,372,308,274,341,293,351,860,181,662,889,926,483,666,669,661,595,17,995,970,874,984,845,819,844,511,567,170,282,163,206,520,931,150,867,81,773,673,216,354,410,127,932,732,403,905,458,281,972,508,379,845,891,939,136,6,853,741,288,397,117,660,357,197,50,132,806,817,561,149,328,666,638,508,255,595,748,619,422,563,771,753,396,326,601,527,529,144,235,853,156,375,581,699,14,144,10,281,13,984,620,48,92,144,451,186,785,12,71,24,25,298,30,492,5,200,337,425,379,954,371,551,5,657,491,419,730,232,308,337,788,50,340,434,557,942,311,824,333,559,232,548,674,988,977,962,413,3,453,739,36,676,379,317,613,96,124,344,204,681,965,612,226,206,215,392,730,178,762,894,857,843,949,244,929,500,715,799,324,463,813,304,731,659,288,694,738,596,591,739,160,245,879,420,67,886,809,259,416,515,772,19,38,97,784,844,71,359,892,566,709,319,978,248,213,987,769,269,783,665,280,412,48,489,580,349,89,917,246,250,530,955,125,945,257,778,344,548,146,352,650,817,677,775,633,388,422,339,42,294,752,726,926,411,990,856,731,42,895,182,377,971,869,137,255,827,101,152,91,267,896,114,958,218,774,885,63,851,352,360,633,54,377,429,238,669,873,166,628,656,407,353,892,771,524,396,744,325,520,471,24,100,665,259,956,965,857,305,717,371,676,276,732,132,752,791,590,811,847,249,165,811,723,121,445,262,787,191,449,295,412,774,874,781,332,436,778,715,365,607,342,587,701,650,779,570,33,452,815,877,642,712,13,552,670,731,936,501,660,63,551,859,413,437,67,284,795,328,740,698,800,565,503,192,247,979,214,81,10,854,25,183,833,452,248,827,223,733,513,398,893,997,874,996,583,88,98,237,583,444,26,957,323,80,930,283,829,838,435,456,221,695,63,440,20,244,239,622,199,28,830,990,152,843,377,669,652,642,133,399,568,702,621,594,229,620,241,606,642,267,999,414,838,721,123,783,490,544,768,377,292,47,620,243,570,866,6,663,620,82,213,515,205,222,783,912,963,142,187,240,576,620,898,350,749,787,116,244,658,689,75,264,901,905,388,569,704,167,514,54,835,203,352,782,154,281,114,355,735,113,331,909,568,428,937,742,747,770,142,860,614,198,338,298,99,493,348,34,674,492,647,617,891,972,112,34,847,8,926,622,512,842,474,32,770,399,6,431,582,528,153,565,263,881,219,140,561,155,683,758,927,683,220,225,746,76,25,164,889,765,335,500,524,172,854,866,93,491,560,147,291,549,919,533,140,159,735,953,407,182,43,505,415,130,109,447,972,115,896,524,178,844,740,452,53,49,696,592,97,606,834,241,114,390,10,951,787,399,721,632,953,839,108,769,433,432,32,940,291,683,406,969,633,860,338,36,872,664,843,440,534,106,811,777,95,884,274,56,789,891,276,551,991,177,189,32,188,725,49,654,929,688,435,975,693,822,309,883,172,659,945,77,767,318,558,471,680,737,469,467,705,452,71,821,973,322,138,379,135,597,773,748,474,152,727,969,76,893,540,910,209,139,726,343,335,838,679,216,511,993,756,715,273,506,194,63,200,748,501,427,588,622,481,132,92,483,332,118,68,167,362,639,962,359,362,915,198,200,389,649,768,179,977,822,728,717,933,297,740,500,335,587,978,153,133,93,197,623,601,894,228,579,711,12,832,123,834,351,856,270,353,956,203,671,117,322,974,60,96,172,501,23,617,26,95,330,782,789,206,162,988,424,508,206,277,907,517,965,96,858,325,434,792,488,394,6,167,468,20,755,669,137,770,766,870,882,865,454,32,753,512,378,442,191,594,412,937,160,502,577,881,830,110,512,739,507,330,820,326,944,720,832,638,629,359,200,584,323,985,351,1,579,994,845,809,837,613,102,546,977,600,364,323,93,612,772,526,256,710,468,957,282,345,466,16,640,368,694,12,360,169,367,429,145,171,934,469,885,120,748,284,364,383,9,52,51,876,372,471,679,984,10,103,341,786,627,800,514,930,795,533,906,905,580,716,222,933,605,874,390,773,234,689,597,585,617,932,949,147,222,482,352,91,233,911,167,341,952,228,843,411,884,350,48,268,64,558,913,218,491,870,370,575,194,847,591,822,91,302,926,99,601,157,184,557,662,20,147,896,501,17,248,750,233,476,957,596,516,39,346,809,511,25,706,769,881,974,990,830,145,424,17,677,751,455,58,929,796,371,697,221,724,49,419,994,848,458,994,961,35,545,886,355,420,606,521,772,690,188,762,295,222,186,25,280,734,898,193,15,475,133,133,494,920,379,115,978,389,480,188,531,83,550,542,665,494,752,618,540,186,407,850,763,444,122,829,568,826,739,532,935,959,539,992,659,130,694,767,170,544,730,631,372,578,369,93,871,935,198,395,681,406,493,505,604,545,143,53,864,232,798,850,44,250,304,522,656,281,642,528,550,656,577,7,516,280,498,617,343,159,426,362,272,1,273,72,147,468,953,405,110,881,96,782,857,304,334,228,13,616,465,991,114,781,319,958,928,250,495,815,574,762,681,341,808,873,388,771,552,458,724,19,862,922,563,943,588,725,645,635,238,970,780,144,284,103,907,212,750,59,823,481,388,652,739,935,947,673,295,725,571,570,708,788,262,767,54,520,498,359,696,170,232,120,249,513,633,250,884,456,683,112,334,665,790,751,294,940,286,315,468,926,95,721,950,754,193,537,265,629,715,315,619,719,309,755,30,255,311,228,48,721,619,36,482,798,677,758,436,262,136,93,865,692,430,827,702,922,80,677,485,602,159,809,696,961,918,532,206,297,441,649,729,814,171,451,26,298,852,529,997,713,430,65,103,432,419,468,171,474,268,644,909,783,716,962,333,614,304,822,228,49,543,308,182,861,282,558,455,585,775,826,739,258,78,979,244,12,424,503,991,400,855,257,291,682,476,751,206,162,41,790,374,448,96,751,912,750,404,740,960,883,817,683,368,438,809,125,708,968,444,781,832,865,674,764,984,35,997,257,68,643,750,799,601,949,969,29,389,48,76,187,573,551,975,300,556,306,151,24,649,273,546,292,783,398,771,101,807,545,332,114,900,528,555,157,451,781,416,660,640,844,951,211,722,377,351,736,540,161,487,192,645,191,384,829,313,670,337,494,361,152,629,505,739,504,179,559,987,86,227,498,343,859,702,66,763,666,3,665,748,880,925,368,378,370,466,932,444,970,53,635,350,272,300,330,123,860,592,411,534,908,397,181,257,602,667,15,779,287,628,895,673,317,117,32,81,703,587,47,40,214,602,84,471,78,254,518,687,629,529,839,811,39,406,24,122,67,458,18,132,998,595,719,92,660,416,839,937,82,398,616,615,113,565,929,660,694,639,631,338,894,517,883,57,365,625,406,369,617,586,781,327,437,687,446,275,48,375,917,344,220,632,517,762,271,910,354,653,159,879,705,700,273,2,260,74,662,230,470,205,391,723,630,598,2,952,721,599,888,712,576,820,879,225,386,383,704,334,20,214,186,833,985,690,665,229,116,902,768,290,872,891,686,567,975,587,76,1000,507,855,167,842,103,858,407,885,651,943,676,384,89,488,163,274,542,979,537,746,105,712,861,786,59,509,589,503,907,179,540,973,540,344,361,534,148,875,305,193,441,228,454,591,985,466,246,122,132,210,134,328,433,766,809,181,415,813,262,70,312,609,458,932,934,99,883,67,756,879,82,117,106,322,630,326,803,866,699,337,596,510,989,205,958,425,907,947,189,121,907,503,36,613,629,820,460,161,21,66,749,921,868,205,134,442,270,80,316,595,519,8,434,808,77,881,183,969,354,46,433,197,504,575,978,230,199,78,326,966,207,693,93,449,626,231,652,180,433,275,268,830,250,619,634,299,408,434,660,80,600,205,560,650,937,920,948,646,345,30,861,785,778,945,734,378,227,889,205,859,35,386,814,217,865,931,25,548,726,23,779,784,192,827,465,633,995,886,577,342,215,119,847,577,244,346,188,142,148,760,14,748,144,130,771,761,542,623,653,852,262,169,218,707,672,62,350,433,709,394,395,693,519,953,649,532,158,499,460,437,471,204,904,991,617,347,948,895,907,451,8,187,989,860,573,44,93,431,707,235,455,913,900,346,78,233,225,109,763,872,554,221,953,460,143,278,24,300,631,699,220,812,666,44,565,643,259,972,530,868,518,928,811,753,142,997,176,590,440,985,570,971,561,32,597,134,720,730,860,756,126,760,314,604,861,532,720,195,837,815,710,142,803,675,714,533,623,54,346,404,469,297,602,367,714,833,751,226,9,99,375,545,672,83,844,366,980,962,673,948,491,34,579,933,319,569,451,967,710,24,282,282,340,491,470,184,797,92,800,295,566,183,326,628,511,129,921,705,854,162,291,251,691,581,30,283,937,655,6,55,855,941,52,901,100,775,964,819,396,801,428,879,154,940,940,749,441,336,665,426,649,773,9,481,590,143,324,55,282,152,28,652,180,477,284,840,321,641,761,31,652,934,550,283,751,394,192,521,729,711,502,772,966,275,620,939,591,475,580,957,841,902,780,538,849,451,451,31,374,635,405,997,419,103,880,301,259,294,61,570,259,81,3,211,88,670,923,741,417,587,322,12,134,775,183,991,861,982,588,96,645,660,344,66,634,970,804,204,763,65,306,876,517,649,694,200,513,96,687,88,282,597,644,541,962,695,738,238,913,18,2,920,410,956,23,528,367,604,3,589,403,752,870,712,322,974,483,217,44,738,111,847,890,36,107,461,350,857,186,56,904,577,541,658,424,9,161,435,252,729,659,618,770,684,638,819,753,797,425,667,950,752,4,802,140,520,396,158,143,849,832,225,231,200,986,486,757,627,15,251,443,530,856,977,453,372,603,873,209,826,937,264,388,664,392,235,948,581,281,501,478,878,568,586,807,443,217,417,567,742,586,54,927,352,247,928,883,492,393,778,450,750,888,680,28,767,312,53,912,919,350,617,934,195,715,542,308,872,688,152,485,454,738,173,159,183,971,686,967,487,758,958,78,769,765,311,664,2,675,338,290,400,313,659,233,800,612,723,162,958,115,348,630,959,431,83,712,968,149,909,445,118,812,898,451,862,795,199,851,855,169,950,996,326,495,64,85,617,605,363,858,164,39,444,861,407,643,755,707,405,538,651,173,726,823,69,720,217,673,663,866,628,298,444,419,948,813,653,10,379,813,699,196,318,792,34,632,482,815,251,325,382,588,387,226,304,108,882,17,984,713,596,353,321,350,348,327,452,51,332,481,407,928,743,526,429,887,62,508,472,704,58,790,86,116,765,769,212,740,71,186,100,682,775,218,765,423,971,157,908,519,573,923,663,93,818,900,952,243,847,323,261,590,629,643,597,97,637,245,849,977,110,501,616,949,231,108,109,442,140,46,774,486,724,810,813,667,747,33,77,337,606,18,910,294,98,588,184,693,851,52,336,785,478,649,238,295,877,308,333,386,370,872,319,519,391,320,111,833,866,216,295,78,484,853,921,427,134,272,508,303,151,765,885,755,189,2,314,372,437,504,609,685,116,887,958,874,298,363,48,853,678,785,661,75,343,902,897,103,375,435,704,363,540,90,790,701,240,721,444,43,3,205,682,656,140,922,284,14,310,988,849,733,562,774,493,613,368,117,18,527,961,776,506,123,603,375,259,637,274,95,140,443,533,363,536,703,113,280,548,144,848,927,863,989,679,272,169,909,849,216,785,184,233,694,435,339,39,519,536,764,304,791,131,670,57,446,20,395,449,524,739,908,801,622,407,797,407,472,351,612,428,27,789,631,218,149,677,168,17,140,422,680,55,240,877,537,496,700,132,315,255,94,325,47,239,754,473,541,404,443,130,495,895,131,542,158,826,924,790,989,494,222,353,499,863,151,7,144,820,864,451,653,683,893,640,880,875,37,176,192,115,495,7,956,194,103,128,347,908,437,717,781,423,309,821,146,545,514,642,95,647,446,515,267,574,151,798,974,635,701,255,603,229,702,896,570,840,132,439,526,502,711,253,186,905,442,641,856,633,902,420,484,10,87,353,585,248,720,63,565,712,713,697,515,558,481,563,238,720,944,260,255,227,199,180,628,276,711,689,743,96,996,339,668,171,443,233,323,142,965,651,192,205,53,701,915,996,528,514,456,803,488,183,1,157,499,311,222,388,801,655,560,649,191,188,689,652,828,113,694,458,688,356,597,582,584,577,518,156,171,248,103,84,460,1,381,838,288,610,681,804,887,880,782,247,819,135,849,269,624,239,193,506,869,722,220,468,35,683,718,532,132,444,66,698,87,37,884,61,31,830,309,723,35,774,78,618,750,485,157,236,931,44,136,38,27,73,412,620,155,158,958,539,678,785,761,118,887,964,970,11,649,838,138,406,883,561,904,180,699,939,513,874,798,288,168,45,591,353,340,741,745,694,428,672,34,537,69,77,475,982,80,620,361,364,336,271,139,494,30,530,727,35,537,68,278,419,78,681,510,581,283,436,690,685,492,69,923,145,333,751,868,769,524,37,716,736,910,908,466,258,894,140,344,501,905,872,446,441,126,707,519,903,735,94,918,387,243,150,172,855,510,282,299,446,147,280,407,567,895,878,553,936,260,733,566,760,261,279,953,546,777,441,494,440,641,674,653,871,385,63,25,742,960,200,80,81,629,756,814,115,765,719,461,593,549,758,886,65,499,938,462,314,241,270,247,542,215,292,537,11,632,1000,897,140,727,943,818,923,180,203,503,282,706,112,88,236,804,209,602,778,58,960,89,448,907,382,938,580,208,299,409,273,695,124,391,29,997,65,869,684,435,832,872,5,298,86,641,745,554,172,65,405,546,579,96,430,929,352,178,450,758,262,268,921,955,972,419,91,144,261,604,390,988,358,56,419,344,209,958,125,696,902,75,56,510,374,394,583,825,533,61,124,45,571,947,200,928,813,717,351,936,998,92,198,958,864,274,110,646,677,619,593,10,459,925,311,322,631,3,523,746,726,919,504,303,649,814,379,462,538,950,455,154,527,463,827,556,122,229,364,583,345,583,86,6,408,626,685,900,281,11,480,416,317,393,678,636,988,383,936,976,803,155,533,174,574,741,305,156,844,340,231,973,98,191,575,542,707,901,97,371,58,225,877,497,912,664,807,885,943,112,846,719,385,388,140,389,60,73,85,760,167,714,767,841,888,791,730,751,576,594,584,38,246,93,586,1,617,38,231,324,5,334,536,547,344,242,348,612,461,351,686,607,568,128,59,31,239,150,216,489,419,555,165,858,514,103,732,919,378,825,281,32,441,226,804,732,882,464,825,672,831,325,732,453,154,722,737,639,66,318,174,76,280,131,941,660,876,332,840,574,185,876,997,311,717,59,814,178,901,812,696,37,643,460,464,415,933,75,814,516,594,386,38,886,310,550,304,757,231,849,55,640,716,88,589,380,58,827,225,580,210,182,877,121,153,800,104,26,844,494,521,801,331,41,2,124,173,251,426,425,743,397,21,637,716,181,583,80,91,574,957,685,932,205,932,454,170,179,379,862,473,43,585,57,581,201,537,241,231,788,744,886,760,332,959,551,828,821,54,569,437,562,711,327,435,614,530,470,197,120,222,833,503,991,834,513,8,899,605,722,212,903,976,917,559,122,159,775,446,702,461,278,168,520,141,193,279,398,260,146,521,226,571,13,384,122,13,483,196,408,16,125,824,199,928,125,650,925,471,314,97,92,686,703,116,145,973,929,938,875,859,751,836,501,779,140,80,610,257,992,237,240,369,748,657,526,580,327,989,795,191,36,416,940,552,188,812,427,849,654,574,171,348,142,806,438,711,413,17,325,148,328,898,201,760,793,923,440,71,721,589,188,827,58,777,571,295,282,41,531,37,462,336,441,183,642,146,516,708,689,900,856,580,137,967,918,802,645,590,900,913,143,264,290,688,314,984,561,968,297,695,160,996,1,536,35,567,465,358,553,816,131,627,772,98,45,929,581,593,878,789,765,106,13,767,748,481,736,218,363,438,712,847,810,697,556,228,118,949,995,53,487,288,572,706,501,234,396,307,581,819,309,911,708,640,960,163,47,602,793,777,488,235,572,591,611,351,20,270,969,42,551,456,32,421,908,244,49,810,371,482,123,844,370,794,765,541,277,782,521,341,718,442,454,40,85,889,648,488,230,134,47,444,976,583,785,966,847,650,576,898,711,532,848,110,385,150,889,999,523,328,891,305,698,674,111,926,90,789,284,590,152,72,326,486,886,587,837,354,270,261,280,354,947,795,874,510,777,570,134,434,467,103,433,735,571,135,693,409,577,223,319,204,700,902,874,807,268,192,488,496,292,560,479,439,523,118,183,444,758,669,486,559,129,529,663,223,623,627,88,673,901,582,442,545,367,778,135,871,716,311,703,726,251,958,914,601,283,301,447,964,305,658,238,795,548,348,40,969,379,713,266,500,116,915,434,415,700,316,595,884,720,331,50,779,542,98,166,366,608,9,393,341,933,89,268,109,879,647,587,203,975,64,59,958,180,570,446,423,306,165,54,152,379,977,591,440,39,15,482,26,644,9,948,38,224,649,126,717,594,299,299,676,143,248,136,76,341,710,77,846,319,115,416,322,990,891,680,868,373,803,604,306,908,360,261,654,548,204,519,510,307,140,72,140,700,694,332,299,448,134,100,986,336,322,286,704,871,952,285,836,528,154,861,43,769,119,107,306,179,329,450,396,353,758,792,197,730,299,5,695,524,169,975,278,232,174,33,48,7,685,116,690,694,759,40,115,620,510,271,444,802,935,189,847,391,80,521,545,817,466,195,333,591,874,561,266,796,325,440,456,251,734,20,337,720,56,359,664,433,482,179,162,283,485,182,357,865,944,156,457,988,166,622,461,181,183,168,16,744,188,333,974,885,241,20,678,359,298,254,178,951,195,849,450,679,345,389,456,983,467,602,910,305,238,231,240,138,541,714,193,275,995,310,719,764,622,595,647,19,246,205,314,512,228,863,458,943,564,663,668,610,548,507,121,906,397,516,110,369,809,245,770,822,497,798,293,863,823,983,172,794,292,576,850,61,221,513,48,235,257,61,516,188,978,376,128,794,483,940,300,565,440,620,144,141,20,965,49,228,46,112,353,95,570,681,374,501,893,315,898,797,801,690,229,57,386,5,281,263,865,457,578,880,838,744,794,981,949,624,775,786,984,605,412,153,737,217,24,59,952,765,719,324,6,864,414,665,913,488,238,57,360,170,334,400,3,364,41,316,989,585,73,139,908,489,32,368,928,585,375,371,595,899,356,102,115,837,521,555,486,947,341,585,542,824,940,732,977,126,72,165,698,428,940,546,326,546,984,612,592,103,718,314,134,726,422,802,399,653,699,378,364,398,880,244,877,584,92,256,853,975,439,619,683,166,159,847,871,925,931,647,547,271,911,272,617,720,172,391,474,949,921,531,972,510,416,52,112,407,945,840,121,29,202,873,911,976,166,725,308,144,794,383,806,287,186,879,820,551,60,493,792,464,97,787,877,503,736,537,180,860,409,627,517,491,763,868,592,674,907,160,900,334,467,253,172,240,326,997,437,361,130,150,271,177,500,22,63,455,451,38,888,317,903,297,960,60,232,355,852,469,907,513,32,944,195,52,63,38,659,970,166,242,290,306,171,918,373,217,360,434,953,924,909,251,242,602,926,963,276,85,623,720,767,670,819,313,434,35,411,342,775,19,906,457,441,828,324,781,855,573,367,31,187,624,762,458,535,308,45,571,824,769,749,264,289,557,433,754,659,813,882,435,696,189,920,114,199,159,868,949,7,100,43,80,364,511,146,45,567,875,466,213,511,935,339,992,715,54,245,153,691,964,22,245,978,141,627,950,997,628,620,75,79,633,976,465,85,478,269,196,561,751,113,719,681,730,859,381,13,355,32,569,650,428,110,163,807,253,823,842,411,63,984,133,673,549,40,978,988,822,119,908,588,148,514,369,94,997,344,292,219,427,722,22,543,534,376,5,94,816,23,947,936,773,242,804,853,364,787,301,27,553,998,863,235,740,297,811,531,954,85,549,534,773,842,781,499,214,630,4,541,249,483,518,972,394,619,382,655,189,679,304,465,416,570,47,839,705,190,77,227,673,727,223,129,55,651,749,630,206,35,496,577,428,595,414,630,849,328,664,334,729,49,847,553,441,633,885,808,752,949,564,81,484,348,552,948,685,339,498,83,861,738,578,671,723,712,156,337,118,698,622,206,261,389,693,20,450,242,795,694,585,119,257,941,817,932,356,337,915,742,221,134,130,760,733,73,514,800,208,317,498,7,466,107,411,258,597,551,519,920,126,280,571,322,904,721,816,842,734,743,265,855,85,348,197,727,695,624,969,115,215,587,240,368,28,837,622,813,782,490,468,849,433,62,163,238,457,617,480,910,746,778,771,562,562,343,368,981,717,788,503,603,812,344,846,661,92,504,427,37,45,23,157,603,522,583,120,423,160,400,591,434,317,989,176,637,658,841,86,766,16,650,636,306,880,964,986,762,321,6,353,248,227,186,886,221,532,654,30,901,861,448,102,766,220,34,647,493,106,521,81,40,794,903,66,142,466,294,981,379,175,453,540,790,638,567,486,115,278,264,362,755,181,564,47,965,89,937,107,97,713,138,762,972,731,685,700,1,929,399,102,827,641,666,115,744,280,226,206,641,149,433,11,540,872,171,923,832,588,984,323,664,155,686,826,718,572,577,334,482,555,319,266,132,691,323,985,856,943,877,519,673,417,422,867,507,352,978,858,613,542,814,631,405,850,81,862,515,949,980,574,540,722,858,621,194,799,362,509,15,733,157,167,435,853,681,733,422,339,254,1,667,294,568,22,150,415,962,856,676,801,979,63,227,1,521,947,593,492,252,487,220,267,652,140,116,423,140,834,527,749,538,159,867,166,489,18,641,827,838,850,448,991,90,125,253,259,422,225,616,853,751,317,517,184,814,240,930,207,600,650,133,717,132,606,933,432,266,161,302,275,988,727,140,199,404,357,739,423,199,62,970,72,485,574,361,202,26,239,205,917,245,965,916,605,271,512,145,835,903,509,477,676,177,987,471,86,589,164,893,824,110,242,571,978,74,59,496,497,12,461,80,309,556,233,953,775,12,764,391,124,829,357,916,398,201,357,982,418,346,826,886,519,844,440,923,526,797,762,170,538,908,434,291,869,916,663,196,734,564,49,596,891,974,583,586,886,249,927,83,862,250,865,40,531,690,210,686,107,894,56,588,457,338,693,582,133,142,649,553,417,825,854,449,598,544,580,700,566,959,832,474,877,636,753,810,27,194,908,456,319,901,14,97,899,703,993,39,58,976,46,181,589,807,559,692,372,948,708,265,438,995,486,746,744,797,115,662,741,553,316,698,899,186,443,154,897,583,453,503,854,763,458,740,623,492,181,846,937,61,70,827,951,795,457,199,312,190,140,444,390,649,959,161,845,104,357,608,171,396,818,468,296,128,812,70,239,754,489,270,337,931,342,372,647,747,131,383,56,749,146,986,91,241,213,873,910,388,103,387,250,94,930,13,730,171,625,444,197,456,806,159,688,563,393,919,658,586,635,906,35,379,330,964,22,379,261,327,513,165,875,619,143,668,263,155,462,774,996,854,182,576,500,111,423,406,144,561,373,104,895,362,755,92,834,741,486,821,64,229,800,909,406,801,69,437,880,320,421,878,24,1,440,713,32,261,12,303,555,110,885,759,737,696,907,276,899,648,572,536,741,348,780,132,287,292,866,701,893,275,992,518,773,602,835,145,216,212,368,198,496,181,462,798,964,872,659,240,689,593,903,53,772,59,149,936,826,989,197,632,381,250,757,48,586,38,41,951,383,234,51,346,403,541,890,749,192,921,499,931,631,95,631,991,427,539,493,113,552,335,448,234,794,822,904,281,312,951,7,876,188,381,420,147,250,248,93,895,75,487,641,240,126,115,76,283,685,772,697,576,44,984,10,575,973,887,351,969,923,709,706,259,607,527,555,380,415,667,518,302,175,156,885,885,822,695,910,946,188,926,527,718,608,51,820,671,276,768,999,292,640,499,638,71,352,704,553,306,981,937,527,126,979,686,827,566,723,512,291,96,9,378,441,943,939,981,25,275,56,665,552,562,890,846,751,998,972,325,94,379,162,102,467,472,773,574,650,383,954,84,799,302,99,739,403,209,558,570,620,679,361,240,567,849,640,619,817,360,174,926,9,114,764,611,11,17,112,949,237,162,136,843,23,358,132,311,994,515,767,436,447,790,125,367,388,934,198,923,688,705,343,544,787,453,734,82,417,80,751,685,255,289,459,803,82,843,251,916,772,728,473,895,722,528,366,743,710,431,749,953,473,752,890,395,716,519,32,189,197,303,908,254,746,450,898,202,125,522,836,767,491,572,6,817,74,469,547,65,476,893,55,21,945,551,130,698,496,101,597,104,806,193,479,249,952,167,694,644,507,642,250,352,649,610,285,34,669,80,700,347,227,199,76,655,347,869,506,954,931,863,657,850,576,152,715,602,386,742,321,229,57,46,254,448,189,85,328,824,52,470,21,697,999,441,825,267,370,335,738,983,28,860,309,101,448,333,694,400,337,537,806,640,99,971,245,801,53,560,118,61,179,360,625,757,296,99,856,653,307,849,140,434,414,455,108,442,99,142,124,765,631,97,34,157,957,344,242,233,544,110,142,866,294,812,163,533,670,249,466,814,171,764,894,551,564,29,275,423,83,626,209,560,708,854,783,64,776,776,687,163,954,289,595,893,380,837,444,648,563,543,394,369,75,596,710,413,711,900,713,598,894,275,172,391,640,61,951,206,706,658,461,727,316,219,855,489,126,951,906,496,386,513,638,390,308,278,762,904,841,707,312,25,722,458,907,182,916,516,482,49,104,387,127,912,537,296,642,257,673,683,39,938,91,154,751,902,38,411,227,981,36,608,218,480,831,954,152,196,286,131,161,141,713,218,780,375,465,339,107,842,641,636,348,570,910,571,35,915,961,460,931,277,590,649,495,960,377,224,606,750,588,305,876,90,862,612,879,1000,826,308,604,713,205,705,111,345,871,180,401,531,213,288,678,622,933,315,766,361,138,732,119,373,435,175,320,744,282,3,924,126,233,889,613,344,217,994,649,479,439,86,351,423,459,734,141,551,914,880,247,809,264,458,471,781,783,736,197,171,747,239,862,397,489,480,706,12,963,657,241,802,965,761,937,827,689,173,961,114,186,669,458,908,773,184,8,462,630,479,381,354,296,464,72,582,630,854,195,671,76,374,900,498,964,368,738,571,715,665,533,919,938,966,711,71,593,286,304,642,125,551,560,609,167,14,224,782,987,645,859,160,180,261,835,142,837,455,72,493,102,870,6,306,152,614,21,425,817,197,423,697,848,346,667,209,210,726,877,417,960,811,881,458,577,26,879,850,45,68,619,977,560,685,540,133,742,601,959,998,74,783,934,516,887,220,202,82,339,485,369,88,227,651,666,629,322,554,323,649,743,18,252,911,525,371,39,228,470,706,491,904,269,273,729,418,520,209,844,903,478,339,25,726,264,860,430,881,848,976,176,1000,516,371,411,163,536,236,174,456,968,713,134,958,655,187,824,278,629,119,205,232,824,905,787,770,115,912,752,393,656,251,66,543,75,262,180,519,468,642,302,324,161,720,489,183,294,171,706,804,621,477,523,668,316,278,718,532,975,584,361,319,675,679,893,601,533,833,103,210,890,201,633,533,609,478,929,755,972,12,896,537,190,520,398,987,255,833,22,63,559,455,980,831,399,97,386,612,348,383,452,838,378,635,211,768,648,452,209,162,569,903,884,950,201,26,855,347,970,640,819,675,218,958,109,191,746,310,291,310,181,682,182,876,480,926,396,858,298,264,391,243,128,3,193,315,330,660,148,398,955,537,686,392,81,37,244,187,906,951,664,27,239,703,264,862,240,333,419,917,703,706,266,347,235,865,132,430,332,916,691,946,852,30,479,114,575,442,823,549,494,145,472,677,4,542,191,929,173,726,763,817,505,257,783,135,988,950,657,391,511,668,597,632,202,27,748,635,197,771,91,101,12,903,913,568,307,194,271,421,387,501,75,553,398,731,713,812,522,920,813,158,292,1,9,746,532,29,975,617,111,253,102,110,797,815,698,180,850,43,409,836,505,666,811,318,769,492,301,805,188,977,314,536,826,917,932,467,254,191,702,800,480,404,882,569,537,183,955,721,356,790,536,405,237,195,423,696,731,819,179,217,271,590,820,259,778,851,585,714,683,957,671,924,808,591,302,827,933,520,171,178,978,129,876,50,434,76,842,635,158,674,351,494,835,181,406,131,739,663,237,563,530,943,745,753,54,268,173,506,999,657,83,357,526,190,829,430,669,439,433,616,15,243,728,879,784,569,676,17,993,467,234,786,670,458,741,264,653,528,72,813,875,46,686,506,40,816,856,744,368,293,883,394,826,679,142,730,467,312,962,881,79,55,679,548,616,728,798,681,198,43,123,403,631,246,531,793,721,927,3,939,390,746,57,2,523,640,131,36,669,188,957,9,368,519,343,291,676,425,990,301,587,782,141,93,185,653,779,502,369,934,379,943,412,81,543,692,786,27,58,402,671,998,999,2,322,569,865,745,920,567,867,192,774,758,953,784,119,332,480,808,694,595,648,673,528,916,698,814,268,829,501,38,462,717,570,674,779,292,598,892,86,666,854,766,683,701,170,231,501,404,166,161,312,755,227,761,391,718,997,962,716,516,67,797,164,979,51,69,858,281,326,972,840,282,953,432,860,892,174,555,81,410,197,158,324,479,861,913,844,473,165,974,524,973,132,385,225,913,314,536,618,635,662,159,9,749,187,14,536,678,462,282,339,490,629,853,925,869,444,505,80,741,31,943,671,892,825,101,176,699,454,664,296,865,170,706,579,273,486,926,510,747,378,862,400,205,989,261,724,679,581,331,805,337,48,495,327,856,580,601,419,590,875,844,619,133,343,160,737,175,475,756,124,720,93,134,697,401,971,793,262,26,150,633,550,901,6,407,168,437,747,493,246,108,941,314,470,661,231,451,428,188,416,770,783,115,740,434,490,568,253,308,247,520,197,635,268,474,184,533,551,902,175,601,207,396,568,337,201,461,792,496,573,744,882,736,240,341,106,114,75,468,40,364,295,737,125,69,504,736,508,633,310,187,419,898,232,138,137,1000,737,179,675,253,633,868,64,552,876,326,415,488,346,580,59,9,582,337,812,891,334,708,822,575,607,602,756,83,701,547,390,289,973,974,237,755,470,263,700,677,776,610,972,152,331,253,192,380,736,291,946,110,530,787,696,879,676,805,739,189,662,748,252,374,412,690,153,485,674,78,65,977,347,40,368,543,641,661,147,526,392,107,841,235,994,515,253,200,615,538,733,44,307,535,666,595,966,637,640,487,100,641,658,257,359,201,672,243,821,386,783,515,241,667,253,893,142,190,941,64,163,546,435,638,616,406,874,477,520,147,639,722,436,952,641,738,125,679,61,910,319,63,713,544,504,337,506,828,488,580,518,622,496,984,243,899,984,8,662,823,343,494,849,867,157,964,572,984,861,58,997,200,109,141,486,360,70,937,596,394,908,960,118,512,14,755,874,542,67,311,604,446,124,824,403,486,416,422,720,86,81,536,202,45,588,956,438,231,427,540,334,371,911,781,71,270,808,803,278,421,659,818,876,250,624,431,251,595,706,688,162,680,953,308,703,265,57,454,722,803,756,150,754,347,242,904,758,611,98,294,380,215,593,352,292,141,453,751,800,157,508,935,510,835,216,581,119,697,748,656,134,181,504,446,534,907,232,550,30,479,244,223,495,808,458,188,262,623,996,540,791,852,187,3,136,142,420,459,479,113,273,714,726,506,814,310,939,643,130,163,356,651,904,318,751,963,684,725,511,358,964,672,50,718,156,671,920,328,809,257,78,589,378,466,424,185,118,173,885,672,431,11,758,881,906,695,773,656,703,603,761,443,296,85,815,202,54,692,209,141,627,461,56,650,425,807,632,881,929,122,819,591,202,96,639,179,327,973,884,253,281,770,195,210,670,809,912,49,122,530,369,627,462,73,211,60,667,397,880,859,568,823,802,339,101,901,698,193,933,311,292,55,601,722,97,192,899,487,264,768,748,783,326,82,358,300,522,930,514,441,880,240,885,185,102,268,160,350,437,587,156,753,311,776,882,389,241,854,288,811,328,305,819,764,799,755,480,531,412,773,599,175,551,521,831,954,965,748,168,684,95,727,943,587,888,257,462,986,786,926,281,863,897,126,805,522,977,633,758,621,719,566,777,179,446,930,494,116,864,284,907,591,368,312,88,896,609,324,741,414,753,298,705,316,679,125,599,962,89,515,95,993,487,847,139,732,986,721,785,73,80,967,276,580,606,319,838,219,245,996,398,227,907,821,116,316,292,456,11,945,584,556,969,222,236,506,890,370,168,962,964,713,591,578,102,337,153,192,252,787,238,694,779,905,123,356,876,881,800,839,675,701,278,67,8,672,766,501,350,726,475,5,959,998,516,233,85,457,385,395,90,110,545,802,635,793,302,27,191,685,192,841,813,47,293,536,521,737,86,586,238,338,528,603,463,678,678,965,163,20,385,413,777,56,486,852,46,240,482,881,406,499,115,132,294,324,823,335,243,277,196,537,313,612,490,817,765,131,436,781,667,172,177,105,651,759,663,45,300,959,645,131,88,441,960,780,117,677,676,382,579,124,137,792,783,191,318,451,319,400,699,549,477,565,150,51,974,946,673,847,139,550,746,208,141,81,172,49,816,633,191,596,793,605,293,932,293,827,504,812,592,621,163,199,13,503,521,952,334,772,74,471,638,438,721,432,520,742,685,777,728,29,181,964,385,493,950,220,354,650,899,520,354,229,871,116,31,536,501,533,70,245,7,717,676,699,349,545,839,676,174,790,926,779,466,690,284,767,434,26,762,976,679,17,342,630,341,365,337,383,190,636,15,744,696,99,615,766,418,1000,808,298,839,319,162,829,592,246,200,309,423,807,704,742,995,122,96,17,989,709,297,323,251,619,522,151,388,389,930,611,343,586,316,133,797,877,690,152,570,997,202,346,726,220,885,38,210,759,960,610,637,852,192,268,400,824,110,370,115,41,399,253,155,624,719,123,9,816,227,209,190,564,481,979,748,185,686,458,230,392,176,603,501,201,865,356,226,139,840,438,781,374,37,565,770,584,907,24,3,270,477,422,780,962,746,759,274,519,340,300,97,573,133,645,844,821,148,107,37,254,684,764,269,153,942,869,834,770,647,140,291,712,40,470,471,649,832,468,981,260,387,799,805,865,111,766,717,670,672,805,310,746,337,812,298,510,841,321,652,199,911,428,706,816,380,808,178,853,16,32,558,229,664,601,819,245,188,229,671,630,244,442,967,237,752,693,47,144,701,721,971,189,74,78,484,194,122,81,934,159,171,757,33,832,161,143,943,959,279,335,306,572,959,894,31,466,622,257,772,48,603,287,941,698,722,117,384,353,652,750,106,827,616,377,29,67,266,156,860,582,466,872,848,720,175,838,459,882,614,472,15,427,232,511,843,912,86,590,241,993,107,661,92,147,405,207,607,314,796,920,526,927,805,970,471,907,835,692,944,996,943,867,320,391,746,909,452,963,402,549,805,801,906,478,915,12,398,355,121,242,206,574,65,74,208,688,386,193,258,681,178,486,491,103,53,500,285,181,365,219,250,340,706,413,54,45,8,264,727,500,960,169,671,167,758,3,281,865,110,185,524,132,787,413,392,989,6,584,787,133,541,612,665,581,94,963,874,803,664,362,971,416,899,430,694,122,61,782,740,520,661,403,329,614,955,492,688,699,703,133,891,727,398,893,289,715,922,815,24,478,852,862,513,30,761,254,413,931,206,629,840,298,830,496,56,76,946,719,825,661,436,552,104,839,891,677,966,27,917,868,978,620,26,279,267,731,769,857,539,498,462,893,692,36,764,80,844,168,260,605,863,686,263,85,423,463,579,964,996,382,229,105,946,679,45,481,538,142,834,5,973,31,51,341,598,389,172,574,533,411,432,943,867,114,857,280,371,590,141,687,838,355,826,776,57,20,35,575,100,596,590,315,861,358,254,354,557,832,491,991,826,807,894,85,661,37,504,956,12,477,823,603,662,723,392,782,179,805,33,21,721,463,595,854,966,659,593,899,521,952,705,39,230,360,428,441,480,479,135,467,322,640,122,69,527,334,981,723,293,475,504,32,870,602,554,180,82,408,777,928,717,789,852,76,118,991,901,828,175,932,285,7,339,17,170,542,23,844,507,800,718,819,40,598,303,972,37,361,692,599,249,771,821,506,615,9,54,458,624,499,667,21,802,101,890,529,149,254,680,860,191,304,191,736,954,869,752,712,316,783,687,638,381,145,285,372,99,998,742,912,667,698,457,961,415,534,848,932,588,114,78,629,870,263,371,950,52,690,272,219,494,507,227,453,777,656,921,396,680,544,677,104,615,136,145,101,218,853,103,642,648,744,762,336,182,481,175,195,161,13,682,410,389,372,83,48,610,838,740,273,365,146,186,895,381,510,698,154,863,291,629,510,810,800,986,296,205,924,358,148,801,549,654,292,696,237,143,902,550,622,804,246,379,409,776,972,653,215,50,149,953,504,726,960,940,762,901,160,64,275,986,811,61,666,415,444,357,22,6,823,650,329,645,491,846,857,344,963,401,151,125,11,410,687,427,336,955,687,217,90,274,480,489,685,643,90,307,120,625,805,610,838,582,279,868,490,104,217,983,685,515,86,461,634,810,189,30,576,619,735,483,148,617,917,722,270,185,220,864,250,396,324,62,490,603,369,609,476,327,424,600,635,489,70,243,23,792,714,661,32,276,219,560,166,997,573,340,917,857,772,521,783,203,123,471,710,641,189,470,851,132,237,391,281,537,708,539,891,800,226,564,387,573,204,971,248,382,1000,456,581,559,650,98,258,697,928,93,233,570,331,201,605,843,317,985,961,590,156,24,202,944,868,683,774,498,54,228,716,968,116,623,744,965,575,792,274,999,715,47,176,493,11,842,998,322,84,724,423,241,936,263,783,735,645,361,938,456,626,893,308,784,559,201,534,310,364,982,26,40,732,527,115,352,559,742,917,411,668,697,810,874,933,971,24,688,242,85,997,639,853,636,425,370,154,438,605,909,174,805,846,122,276,397,86,582,215,437,602,911,66,198,77,302,865,354,126,1,972,270,601,870,967,355,553,219,364,727,177,523,687,437,169,42,370,476,610,367,178,446,650,527,880,434,278,219,789,352,583,907,855,702,305,994,633,407,710,287,846,777,100,21,801,546,113,721,813,800,91,929,123,870,731,852,898,657,983,957,249,402,309,555,305,963,981,99,948,698,146,591,378,474,97,732]" to 11
            doWork(testCase3)
        }

        private fun doWork(data: Pair<String, Int>) {
            val solution = Solution()

            val result: Int
            val time = measureTimeMillis { result = solution.jump(LeetUtils.stringToIntArray(data.first)) }

            println("Data:     ${data.first}")
            println("Expected: ${data.second}")
            println("Result:   $result\n")
            println("Time:     $time\n")
        }

    }
}
