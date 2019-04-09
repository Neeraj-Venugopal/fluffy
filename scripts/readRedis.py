import redis
# import cql
# import exceptions
from cassandra.cluster import Cluster
from cassandra.query import BatchStatement

cluster = Cluster(['localhost'])
session = cluster.connect('FluffyClientData')

session.execute("CREATE TYPE RedisData (payload text, origin text, path text)")
session.execute("CREATE TABLE storeClientPayload (key text PRIMARY KEY, data frozen<RedisData>)")

class Data(object):
    """docstring for ClassName"""
    def __init__(self, payload, path, origin):
        self.payload = payload
        self.path = path
        self.origin = origin
        

user_track_stmt = session.prepare(
    "INSERT INTO storeClientPayload (key, data) VALUES (?, ?)")

# add the prepared statements to a batch
batch = BatchStatement()
batch.add(user_track_stmt,
  ["hendrix", "email changed", datetime.utcnow(), "test"])

# execute the batch


redisClient = redis.Redis(host='localhost', port=6379, db=0)
# list = []
# for key in r.scan_iter("*"):
#     # delete the key
#     print (r.get(key))

keys = redisClient.keys('*')
for key in keys:
    type = redisClient.type(key)
    # if type == KV:
    #     val = redis.get(key)
    # if type == "HASH":

    # vals = redisClient.hgetall(key)
    payload = redisClient.hget(key,"payload")
    origin = redisClient.hget(key,"origin")
    path = redisClient.hget(key,"path")
    session.execute(user_track_stmt,[key,Data(payload,path,origin)])
    print(vals)

    # if type == ZSET:
    #     vals = redis.zrange(key, 0, -1)